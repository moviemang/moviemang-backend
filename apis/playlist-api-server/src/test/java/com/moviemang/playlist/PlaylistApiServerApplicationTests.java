package com.moviemang.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.exception.MovieApiException;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.utils.httpclient.HttpClient;
import com.moviemang.datastore.domain.PlayListOrderByLikeDto;
import com.moviemang.datastore.repository.mongo.like.LikeRepository;
import com.moviemang.datastore.repository.mongo.playList.PlaylistRepository;
import com.moviemang.datastore.repository.mongo.tag.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDate;
import java.util.ArrayList;
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

    String API_KEY = "cbfa45007409cc068286bfeafd12a530";
    String BASE_URL = "https://api.themoviedb.org/3/";

    String IMG_BASE_URL = "https://image.tmdb.org/t/p/w500";

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

    // IMG_BASE_URL = https://image.tmdb.org/t/p/w500
    @Test
    @DisplayName("좋아요 순 플레이리스트 조회 API테스트")
    void aggresionTest(){
        Map<String, Object> param = new HashMap<>();
        param.put("api_key", API_KEY);
        HttpClientRequest request = new HttpClientRequest();

        // 좋아요 테이블에서 전날 기준으로 필터
        // targetId로 groupBy하고 Count
        // 그리고 해당 targetId로 플레이리스트 테이블에서 조회
        Aggregation likeAggregation = Aggregation.newAggregation(
                Aggregation.project("targetId", "regDate", "likeType"),
                Aggregation.match(Criteria.where("regDate").gte(LocalDate.now().minusDays(2)).and("likeType").is("D")),
                Aggregation.group("targetId").count().as("likeCount")
        );

        List<PlayListOrderByLikeDto> filterByTypeAndGroupByTargetId = likeRepository.filterByTypeAndGroupByTargetId(likeAggregation, "like")
                .getMappedResults()
                .stream()
                .sorted((o1, o2) -> Integer.compare(o2.getLikeCount(), o1.getLikeCount()))
                .limit(4)
                .map( likeObj -> {
                    PlayListOrderByLikeDto playListOrderByLikeDto = playlistRepository.findPlayListDtoBy_id(likeObj.get_id());
                    playListOrderByLikeDto.setLikeCount(likeObj.getLikeCount());
                    List<String> imgPathList = new ArrayList<>();
                    List<Integer> movieIds = playListOrderByLikeDto.getMovieIds();
                    for(int movieId : movieIds){
                        request.setUrl(BASE_URL + "/movie/"+ movieId  +"/images");
                        request.setData(param);
                        try {
                            Map<String, Object> response = om.readValue(HttpClient.get(request), HashMap.class);
                            if(response.get("status_code") != null){
                                if(StringUtils.equals(response.get("status_code").toString(), "7") ){
                                    throw new MovieApiException(response.get("status_massage").toString(), ErrorCode.INVALID_API_KEY);
                                }
                                throw  new MovieApiException(response.get("status_message").toString(), ErrorCode.RESOURCE_NOT_FOUND);
                            }
                            List<Map<String, Object>> posterData = (List<Map<String, Object>>) response.get("posters");
                            imgPathList.add(IMG_BASE_URL + posterData.get(0).get("file_path").toString());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    playListOrderByLikeDto.setRepresentativeImagePath(imgPathList);
                    return playListOrderByLikeDto;
                })
                .collect(Collectors.toList());;

        for(PlayListOrderByLikeDto playlist : filterByTypeAndGroupByTargetId){
            log.info("플레이리스트 : {}", playlist.toString());
        }
    }





}
