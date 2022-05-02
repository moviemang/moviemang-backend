package com.moviemang.playlist;

import com.moviemang.datastore.repository.mongo.like.LikeRepository;
import com.moviemang.datastore.repository.mongo.playList.PlaylistRepository;
import com.moviemang.datastore.repository.mongo.tag.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;

@Slf4j
@SpringBootTest
class PlaylistApiServerApplicationTests {

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    LikeRepository likeRepository;


//    @DisplayName("태그 넣기")
//    @Test
//    void tagInsert(){
//        Tag tag = Tag.builder()
//                .tagName("태그3")
//                .build();
//        tagRepository.save(tag);
//    }

//    @DisplayName("좋아요 넣기")
//    @Test
//    void likeInsert(){
//        Like like = Like.builder()
//                .memberId(1)
//                .
//
//    }

//    @DisplayName("플레이리스트 넣기")
//    @Test
//    void playListInsert(){
//
//        List<Tag> tagList = tagRepository.findAll();
//        ArrayList<Integer> movieIds = new ArrayList<>();
//        movieIds.add(2);
//        movieIds.add(4);
//
//        Playlist playlist = Playlist.builder()
//                .playListTitle("플레이리스트3")
//                .memberId(2)
//                .playListDescription("그냥 시간 날 때 들음")
//                .tags(tagList)
//                .movieIds(movieIds)
//                .display("disable")
//                .build();
//        playlistRepository.save(playlist);
//    }

    @Test
    @DisplayName("LEFT OUTER JOIN 테스트")
    void aggresionTest(){
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("like")
                .localField("_id")
                .foreignField("targetId")
                .as("likeList");
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);
        playlistRepository.playListLookupLike(aggregation, "playList")
                .getMappedResults()
                .stream()
                .forEach(x -> log.info("테스트  : {}", x.toString()));
    }

}
