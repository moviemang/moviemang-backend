package com.moviemang.playlist.service;

import com.google.common.collect.Maps;
import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.model.vo.PageInfo;
import com.moviemang.datastore.config.MovieApiConfig;
import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.repository.mongo.playlist.PlaylistRepository;
import com.moviemang.playlist.dto.MyPlaylist;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.mapper.PlaylistMapper;
import com.moviemang.playlist.util.ImgRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlaylistServiceImpl implements PlaylistService{

    private PlaylistRepository playlistRepository;
    private PlaylistMapper playlistMapper;
    private MovieApiConfig movieApiConfig;
    private ImgRequestUtil imgRequestUtil;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper, ImgRequestUtil imgRequestUtil,
                               MovieApiConfig movieApiConfig) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
        this.imgRequestUtil = imgRequestUtil;
        this.movieApiConfig = movieApiConfig;
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
        HttpClientRequest request = new HttpClientRequest();
        Map<String, Object> param = Maps.newHashMap();
        param.put("api_key", movieApiConfig.getMovieApiProperties().getApiKey());
        request.setData(param);

        Aggregation likeAggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("regDate").gte(LocalDate.now().minusDays(1))),
                Aggregation.lookup("like", "_id", "targetId", "likes"),
                Aggregation.match(Criteria.where("display").is(true).and("movieIds").not().size(0))
        );

        List<PlaylistInfo> filterByTypeAndGroupByTargetId = playlistRepository.playlistOrderByLike(likeAggregation, "playlist")
                .getMappedResults()
                .stream()
                .map( playlistLikeJoin -> imgRequestUtil.requestImgPath(movieApiConfig, request, playlistLikeJoin))
                .sorted((p1, p2) -> Long.compare(p2.getLikeCount(), p1.getLikeCount()))
                .limit(4)
                .collect(Collectors.toList());

        return CommonResponse.success(filterByTypeAndGroupByTargetId);
    }
}
