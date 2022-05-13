package com.moviemang.playlist.service;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.coreutils.model.vo.PageInfo;
import com.moviemang.datastore.domain.PlaylistOrderByLikeDto;
import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.playlist.dto.MyPlaylist;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.mapper.PlaylistMapper;
import org.apache.commons.collections4.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.exception.MovieApiException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.utils.httpclient.HttpClient;
import com.moviemang.datastore.config.MovieApi;
import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.datastore.repository.mongo.like.LikeRepository;
import com.moviemang.datastore.repository.mongo.playlist.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import java.util.List;

@Service
@Slf4j
public class PlaylistServiceImpl implements PlaylistService{

    private PlaylistRepository playlistRepository;
    private PlaylistMapper playlistMapper;
    private LikeRepository likeRepository;
    private MemberRepository memberRepository;
    private ObjectMapper om;
    private MovieApi movieApi;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper, LikeRepository likeRepository, MemberRepository memberRepository, ObjectMapper om, MovieApi movieApi) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
        this.om = om;
        this.movieApi = movieApi;
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
        param.put("api_key", movieApi.getAPI_KEY());
        HttpClientRequest request = new HttpClientRequest();

        AggregationOperation lookupOperation = context -> new Document(
                "$lookup",
                new Document("from", "playlist")
                        .append("let", new Document("targetId",  "$_id"))
                        .append("pipeline", Collections.singletonList(new Document("$match", new Document("$expr", new Document("$and",
                                Arrays.asList(
                                        new Document("$eq", Arrays.asList("$_id", "$$targetId")),
                                        new Document("$eq", Arrays.asList("$display", true))
                                ))))
                        ))
                        .append("as","playlist")
        );

        Aggregation likeAggregation = Aggregation.newAggregation(
                Aggregation.project("targetId", "regDate", "likeType"),
                Aggregation.match(Criteria.where("regDate").gte(LocalDate.now().minusDays(1)).and("likeType").is("M")),
                Aggregation.group("targetId").count().as("likeCount"),
                Aggregation.sort(Sort.Direction.DESC, "likeCount"),
                Aggregation.limit(4),
                lookupOperation,
                Aggregation.match(Criteria.where("playlist").not().size(0))
        );

        List<PlaylistOrderByLikeDto> filterByTypeAndGroupByTargetId = likeRepository.filterByTypeAndGroupByTargetId(likeAggregation, "like")
                .getMappedResults()
                .stream()
                .map( playlistLikeJoin -> {
                    Playlist playlistInfo = playlistLikeJoin.getPlaylist().get(0);
                    List<String> imgPathList = new ArrayList<>();
                    List<Integer> movieIds = playlistInfo.getMovieIds();
                    for(int movieId : movieIds){
                        request.setUrl(String.format("%s/movie/%d/images", movieApi.getBASE_URL(), movieId));
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
                            imgPathList.add(movieApi.getIMG_BASE_URL() + posterData.get(0).get("file_path").toString());

                        } catch (Exception e) {
                            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
                        }
                    }

                    return PlaylistOrderByLikeDto.builder()
                            ._id(playlistInfo.get_id())
                            .playlistTitle(playlistInfo.getPlaylistTitle())
                            .memberName(memberRepository.findByMemberId(playlistInfo.getMemberId())
                                    .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND))
                                    .getMemberName())
                            .representativeImagePath(imgPathList)
                            .tags(playlistInfo.getTags())
                            .movieCount(playlistInfo.getMovieIds().size())
                            .likeCount(playlistLikeJoin.getLikeCount())
                            .build();
                })
                .collect(Collectors.toList());

        return CommonResponse.success(filterByTypeAndGroupByTargetId);
    }
}
