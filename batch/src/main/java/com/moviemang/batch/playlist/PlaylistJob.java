package com.moviemang.batch.playlist;

import com.moviemang.batch.listener.CommonJobListener;
import com.moviemang.batch.listener.CommonStepListener;
import com.moviemang.datastore.dto.playlist.PlaylistWithPrevLikeCount;
import com.moviemang.datastore.entity.mongo.Like;
import com.moviemang.datastore.entity.mongo.PrevLikes;
import com.moviemang.playlist.util.ImgRequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class PlaylistJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;

    private final ImgRequestUtil imgRequestUtil;

    @Bean
    public Job executePlaylistJob() {
        return jobBuilderFactory.get("executeJob")
                .start(filterPrevDayLikes())
                .next(lookupTasklet())
                .listener(new CommonJobListener())
                .build();
    }

    @Bean(name = "filterPrevDayLikes")
    @JobScope
    @SuppressWarnings("unchecked")
    public Step filterPrevDayLikes() {
        return stepBuilderFactory.get("filterPrevDayLikes")
                .<Like, PrevLikes>chunk(10)
                .reader(filteringLike())
                .processor(changeToData())
                .writer(playlistWriter())
                .listener(new CommonStepListener())
                .build();
    }

    @Bean(name = "lookupTasklet")
    @JobScope
    @SuppressWarnings("unchecked")
    public Step lookupTasklet() {
        return stepBuilderFactory.get("lookupTasklet")
                .tasklet(aggregationTask())
                .build();
    }

    @Bean
    @StepScope
    public MongoItemReader<Like> filteringLike() {
        Query query = new Query()
                .addCriteria(Criteria.where("regDate")
                        .gte(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00.000", Locale.KOREA)))
                        .lte(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'23:59:59.000", Locale.KOREA)))
                );

        MongoItemReader<Like> itemReader = new MongoItemReader();
        itemReader.setTemplate(mongoTemplate);
        itemReader.setTargetType(Like.class);
        itemReader.setCollection("like");
        itemReader.setSort(new HashMap<String, Sort.Direction>());
        itemReader.setQuery(query);

        return itemReader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Like, PrevLikes> changeToData(){
        return item -> {
            log.info("필터링 전 Like => {}", item.toString());
            return PrevLikes.builder()
                    ._id(item.get_id())
                    .targetId(item.getTargetId())
                    .regDate(item.getRegDate())
                    .build();
        };
    }

    @Bean
    @StepScope
    public ItemWriter<PrevLikes> playlistWriter() {
        return items -> {
            for(PrevLikes item : items){
                log.info("item ==> {}", item.toString());
                mongoTemplate.save(item);
            }
        };
    }

    @Bean
    @StepScope
    @SuppressWarnings("unchecked")
    public Tasklet aggregationTask() {
        return (contribution, chunkContext) -> {

            Aggregation likeAggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("regDate")
                            .gte(LocalDateTime.parse(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00.000", Locale.KOREA))))
                            .lte(LocalDateTime.parse(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'23:59:59.000", Locale.KOREA))))
                    ),
                    Aggregation.group("targetId")
                            .count().as("likeCount")
                            .max("regDate").as("curRegDate"),
                    Aggregation.lookup("playlist", "_id", "_id", "playlist"),
                    Aggregation.unwind("playlist"),
                    Aggregation.match(Criteria.where("playlist.display").is(true))
            );
            mongoTemplate.aggregate(likeAggregation, "prevLikes", PlaylistWithPrevLikeCount.class)
                    .forEach(mongoTemplate::save);

            return RepeatStatus.FINISHED;
        };
    }
}
