package com.moviemang.playlist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.exception.MovieApiException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.model.vo.PageInfo;
import com.moviemang.coreutils.utils.httpclient.HttpClient;
import com.moviemang.datastore.config.MovieApi;
import com.moviemang.datastore.domain.PlaylistOrderByLikeDto;
import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.config.MovieApiConfig;
import com.moviemang.datastore.domain.PlaylistOrderByLikeDto;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.datastore.repository.mongo.like.LikeRepository;
import com.moviemang.datastore.repository.mongo.playlist.PlaylistRepository;
import com.moviemang.playlist.dto.MyPlaylist;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.mapper.PlaylistMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;

@Service
@Slf4j
public class PlaylistServiceImpl implements PlaylistService{

    private PlaylistRepository playlistRepository;
    private PlaylistMapper playlistMapper;
    private LikeRepository likeRepository;
    private MemberRepository memberRepository;
    private MovieApiConfig movieApiConfig;
    private ObjectMapper objectMapper;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper, LikeRepository likeRepository, MemberRepository memberRepository, MovieApiConfig movieApiConfig, ObjectMapper objectMapper) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
        this.movieApiConfig = movieApiConfig;
        this.objectMapper = objectMapper;
    }

    @Override
    public CommonResponse myPlaylist(CommonParam commonParam, Pageable pageable) {
        MyPlaylist.Response.ResponseBuilder myPlaylist = MyPlaylist.Response.builder();
        try {
            List<Playlist> playlist = playlistRepository.findByMemberId(commonParam.getId(), pageable);
            if (CollectionUtils.isEmpty(playlist)) {
                return CommonResponse.success(ErrorCode.COMMON_EMPTY_DATA);
            }
            List<PlaylistInfo> playlistInfo = playlistMapper.of(playlist);
            myPlaylist.playlist(playlistInfo);
            myPlaylist.page(PageInfo.builder()
                    .page(pageable.getPageNumber())
                    .size(pageable.getPageSize())
                    .build());

        } catch (Exception e){
            log.error(e.getMessage());
            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
        }

        return CommonResponse.success(myPlaylist.build());
    }

    @Override
    public CommonResponse save(com.moviemang.playlist.dto.Playlist.Request playlist) {
        try {
            playlistRepository.save(Playlist.builder()
                    .playlistTitle(playlist.getTitle())
                    .playlistDescription(playlist.getDescription())
                    .memberId(playlist.getId())
                    .display(playlist.isDisplay())
                    .build());
        } catch (Exception e){
            log.error(e.getMessage());
            throw new BaseException(ErrorCode.PLAYLIST_SAVE_FAIL);
        }

        return CommonResponse.success(null);
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
                    int idx = 0;
                    for(int movieId : movieIds){
                        // 우선 플레이리스트 당 영화 이미지 5개만 가져옴. 추후 수정 가능
                        if(idx == 5){
                            break;
                        }
                        request.setUrl(String.format("%s/movie/%d/images", movieApiConfig.getMovieApiProperties().getBaseUrl(), movieId));
                        request.setData(param);
                        try {
                            Map<String, Object> response = objectMapper.readValue(HttpClient.get(request), HashMap.class);
                            if(response.get("status_code") != null){
                                if(StringUtils.equals(response.get("status_code").toString(), "7") ){
                                    throw new MovieApiException(response.get("status_massage").toString(), ErrorCode.INVALID_API_KEY);
                                }
                                continue;
                            }
                            List<Map<String, Object>> posterData = (List<Map<String, Object>>) response.get("posters");
                            imgPathList.add(movieApiConfig.getMovieApiProperties().getImgBaseUrl() + posterData.get(0).get("file_path").toString());
                            idx++;

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
                .sorted((p1, p2) -> Integer.compare(p2.getLikeCount(), p1.getLikeCount()))
                .limit(4)
                .collect(Collectors.toList());

        return CommonResponse.success(filterByTypeAndGroupByTargetId);
    }
}
