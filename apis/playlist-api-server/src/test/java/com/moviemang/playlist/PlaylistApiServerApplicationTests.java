package com.moviemang.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.utils.httpclient.HttpClient;
import com.moviemang.datastore.domain.PlayListOrderByLikeDto;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class PlaylistApiServerApplicationTests {

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ObjectMapper om;


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

    // 기본 URL = https://api.themoviedb.org/3/
    // 대상 URL = /movie/{movie_id}/images
    // API키 = cbfa45007409cc068286bfeafd12a530
    // IMG_BASE_URL = https://image.tmdb.org/t/p/w500
    @Test
    @DisplayName("LEFT OUTER JOIN 테스트")
    void aggresionTest(){
        Map<String, Object> param = new HashMap<>();
        param.put("api_key", "cbfa45007409cc068286bfeafd12a530");
        HttpClientRequest request = new HttpClientRequest();

        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("like")
                .localField("_id")
                .foreignField("targetId")
                .as("likeList");
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);
        List<PlayListOrderByLikeDto> list = playlistRepository.playListLookupLike(aggregation, "playList")
                .getMappedResults()
                .stream()
                .peek(playlist -> {
                    playlist.setLikeCount(playlist.getLikeList().size());
                })
                .map( notImagPlayList -> {
                    List<Integer> movieIds = notImagPlayList.getMovieIds();
                    for(int movieId : movieIds){
                        request.setUrl("https://api.themoviedb.org/3/" + "/movie/"+ movieId  +"/images");
                        request.setData(param);
                        try {
                            Map<String, Object> reseponse = om.readValue(HttpClient.get(request), HashMap.class);
                            List<Map<String, Object>> posterData = (List<Map<String, Object>>) reseponse.get("posters");
                            notImagPlayList.getRepresentativeImagePath().add(posterData.get(0).get("file_path").toString());
                            return notImagPlayList;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return null;
                })
                .collect(Collectors.toList());

        for(PlayListOrderByLikeDto playlist : list){
            log.info("플레이리스트 : {}", playlist.toString());
        }
    }





}
