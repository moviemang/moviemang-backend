package com.moviemang.playlist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.exception.MovieApiException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.utils.httpclient.HttpClient;
import com.moviemang.datastore.config.MovieApiConfig;
import com.moviemang.datastore.domain.PlaylistOrderByLikeDto;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.datastore.repository.mongo.like.LikeRepository;
import com.moviemang.datastore.repository.mongo.playlist.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlaylistServiceImpl implements PlaylistService{

    private PlaylistRepository playlistRepository;
    private LikeRepository likeRepository;
    private MemberRepository memberRepository;
    private ObjectMapper om;
    private MovieApiConfig movieApiConfig;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, LikeRepository likeRepository, MemberRepository memberRepository,
                               MovieApiConfig movieApiConfig, ObjectMapper om){
        this.playlistRepository = playlistRepository;
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
        this.movieApiConfig = movieApiConfig;
        this.om = om;
    }

    @Override
    public CommonResponse playlistOrderByLike() {
        Map<String, Object> param = new HashMap<>();
        param.put("api_key", movieApiConfig.getMovieApiProperties().getApiKey());
        HttpClientRequest request = new HttpClientRequest();

        Aggregation likeAggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("regDate").gte(LocalDate.now().minusDays(1))),
                Aggregation.lookup("like", "_id", "targetId", "likes")
        );

        List<PlaylistOrderByLikeDto> filterByTypeAndGroupByTargetId = playlistRepository.playlistOrderByLike(likeAggregation, "playlist")
                .getMappedResults()
                .stream()
                .map( playlistLikeJoin -> {
                    List<String> imgPathList = new ArrayList<>();
                    List<Integer> movieIds = playlistLikeJoin.getMovieIds();
                    for(int movieId : movieIds){
                        request.setUrl(String.format("%s/movie/%d/images", movieApiConfig.getMovieApiProperties().getBaseUrl(), movieId));
                        request.setData(param);
                        try {
                            Map<String, Object> response = om.readValue(HttpClient.get(request), HashMap.class);
                            if(response.get("status_code") != null){
                                if(StringUtils.equals(response.get("status_code").toString(), "7") ){
                                    throw new MovieApiException(response.get("status_massage").toString(), ErrorCode.INVALID_API_KEY);
                                }
                                continue;
                            }
                            List<Map<String, Object>> posterData = (List<Map<String, Object>>) response.get("posters");
                            imgPathList.add(movieApiConfig.getMovieApiProperties().getImgBaseUrl() + posterData.get(0).get("file_path").toString());

                        } catch (Exception e) {
                            log.error("movie not found error => {}", movieId);
                            throw new BaseException(ErrorCode.NOT_FOUND_MOVIE);
                        }
                    }

                    return PlaylistOrderByLikeDto.builder()
                            ._id(playlistLikeJoin.get_id())
                            .playlistTitle(playlistLikeJoin.getPlaylistTitle())
                            .memberName(memberRepository.findByMemberId(playlistLikeJoin.getMemberId())
                                    .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND))
                                    .getMemberName())
                            .representativeImagePath(imgPathList)
                            .tags(playlistLikeJoin.getTags())
                            .movieCount(playlistLikeJoin.getMovieIds().size())
                            .likeCount(playlistLikeJoin.getLikes().size())
                            .build();
                })
                .sorted((o1, o2) -> Integer.compare(o2.getLikeCount(), o1.getLikeCount()))
                .limit(4)
                .collect(Collectors.toList());

        return CommonResponse.success(filterByTypeAndGroupByTargetId);
    }
}
