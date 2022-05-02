package com.moviemang.datastore.repository.mongo.playList;

import com.moviemang.datastore.domain.PlayListOrderByLikeDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public class CustomizedPlayListRepositoryImpl implements CustomizedPlayListRepository {

    MongoTemplate mongoTemplate;

    public CustomizedPlayListRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public AggregationResults<PlayListOrderByLikeDto> playListLookupLike(Aggregation aggregation, String collection) {
        return mongoTemplate.aggregate(aggregation, collection, PlayListOrderByLikeDto.class);
    }
}
