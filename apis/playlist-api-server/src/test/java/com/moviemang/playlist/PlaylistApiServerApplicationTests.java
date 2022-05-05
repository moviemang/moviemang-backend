package com.moviemang.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.exception.MovieApiException;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.utils.httpclient.HttpClient;
import com.moviemang.datastore.domain.PlaylistOrderByLikeDto;
import com.moviemang.datastore.repository.mongo.like.LikeRepository;
import com.moviemang.datastore.repository.mongo.playlist.PlaylistRepository;
import com.moviemang.datastore.repository.mongo.tag.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
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

        // 좋아요 Collection 검색 조건
        Aggregation likeAggregation = Aggregation.newAggregation(
                Aggregation.project("targetId", "regDate", "likeType"), // 해당 컬럼들만 추출
                Aggregation.match(Criteria.where("regDate").gte(LocalDate.now().minusDays(3)).and("likeType").is("M")), // 전날 기준, 영화만 검색
                Aggregation.group("targetId").count().as("likeCount"),  // 플레이리스트 PK값으로 GROUP핑 한후 Like수 Count
                Aggregation.sort(Sort.Direction.DESC, "likeCount"), // GROUPING한 likeCount를 내림차순으로 정렬
                Aggregation.limit(4) // 상위 4개의 플레이리스트만 추출
        );

        /**
         * filterByTypeAndGroupByTargetId(조건문, 조회할 Collection)
         */
        List<PlaylistOrderByLikeDto> filterByTypeAndGroupByTargetId = likeRepository.filterByTypeAndGroupByTargetId(likeAggregation, "like")
                .getMappedResults()
                .stream()
                .map( likeObj -> {
                    PlaylistOrderByLikeDto playListOrderByLikeDto = playlistRepository.findPlaylistDtoBy_id(likeObj.get_id()); // 좋아요 Document의 targetId로 플레이리스트 조회
                    playListOrderByLikeDto.setLikeCount(likeObj.getLikeCount()); // DTO에 좋아요 횟수 셋팅
                    List<String> imgPathList = new ArrayList<>();   // 포스터 이미지 경로를 담는 List
                    List<Integer> movieIds = playListOrderByLikeDto.getMovieIds(); // 각 영화의 아이디로 Movie Open Api에서 이미지 경로 받아옴
                    for(int movieId : movieIds){
                        request.setUrl(BASE_URL + "/movie/"+ movieId  +"/images");
                        request.setData(param);
                        try {
                            Map<String, Object> response = om.readValue(HttpClient.get(request), HashMap.class);
                            if(response.get("status_code") != null){
                                // API KEY가 유효하지 않을 때 Exception 처리
                                if(StringUtils.equals(response.get("status_code").toString(), "7") ){
                                    throw new MovieApiException(response.get("status_massage").toString(), ErrorCode.INVALID_API_KEY);
                                }
                                // 영화에 대한 정보가 없을 경우 SKIP
                                continue;
                            }
                            // Poster 정보 셋팅
                            List<Map<String, Object>> posterData = (List<Map<String, Object>>) response.get("posters");
                            imgPathList.add(IMG_BASE_URL + posterData.get(0).get("file_path").toString());
                        } catch (Exception e) {
                            // MOVIE OPEN API의 연결상태가 좋지 않을 경우 Exception 처리
                            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
                        }
                    }
                    playListOrderByLikeDto.setRepresentativeImagePath(imgPathList);
                    return playListOrderByLikeDto;
                })
                .collect(Collectors.toList());;

        for(PlaylistOrderByLikeDto playlist : filterByTypeAndGroupByTargetId){
            log.info("플레이리스트 : {}", playlist.toString());
        }
    }





}
