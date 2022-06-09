package com.moviemang.playlist.service;

import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.model.vo.PageInfo;
import com.moviemang.datastore.dto.playlist.PlaylistAggregationResult;
import com.moviemang.datastore.entity.mongo.Like;
import com.moviemang.datastore.entity.mongo.Playlist;
import com.moviemang.datastore.repository.mongo.like.LikeRepository;
import com.moviemang.datastore.repository.mongo.playlist.PlaylistRepository;
import com.moviemang.playlist.dto.*;
import com.moviemang.playlist.mapper.PlaylistMapper;
import com.moviemang.playlist.util.ImgRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlaylistServiceImpl implements PlaylistService{

    private PlaylistRepository playlistRepository;
    private LikeRepository likeRepository;
    private PlaylistMapper playlistMapper;
    private ImgRequestUtil imgRequestUtil;
    private HttpClientRequest httpClientRequest;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, LikeRepository likeRepository,
                               PlaylistMapper playlistMapper, ImgRequestUtil imgRequestUtil,
                               HttpClientRequest httpClientRequest) {
        this.playlistRepository = playlistRepository;
        this.likeRepository = likeRepository;
        this.playlistMapper = playlistMapper;
        this.imgRequestUtil = imgRequestUtil;
        this.httpClientRequest = httpClientRequest;
    }

    @Override
    public CommonResponse detail(PlaylistDetail.Request request) {
        PlaylistDetail.Response.ResponseBuilder playlistDetail = PlaylistDetail.Response.builder();

        try{
            ObjectId id = new ObjectId(request.getPlaylistId());
            Aggregation likeAggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("_id").is(id)),
                    Aggregation.lookup("like", "_id", "targetId", "likes")
            );

            PlaylistAggregationResult aggregationResult =  playlistRepository.playlistDetail(likeAggregation, "playlist");
            if(aggregationResult == null){
                throw new BaseException(ErrorCode.COMMON_ENTITY_NOT_FOUND);
            } else {
                PlaylistInfo playlistInfo = imgRequestUtil.requestImgPath(ImgRequestUtil.REQUEST_CODE.DETAIL, httpClientRequest, aggregationResult);
                return CommonResponse.success(playlistDetail.playlistInfo(playlistInfo).build());
            }

        } catch (Exception e){
            log.error(e.getMessage());
            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
        }
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
    public CommonResponse lastestPlaylist() {
        Aggregation lastestAggregation = Aggregation.newAggregation(
                Aggregation.project("_id","playlistTitle","movieIds","likes","memberId","modDate"
                        ,"playlistDescription","tags","display","regDate","movieIdsLength").and("movieIds").size().as("movieIdsLength"),
                Aggregation.match(Criteria.where("display").is(true).and("movieIdsLength").gte(5)),
                Aggregation.sort(Sort.Direction.DESC,"regDate"),
                Aggregation.limit(4)
        );

        try{
            List<PlaylistInfo> playlists = playlistRepository.lastestPlaylist(lastestAggregation, "playlist")
                    .getMappedResults()
                    .stream()
                    .map( playlist -> imgRequestUtil.requestImgPath(ImgRequestUtil.REQUEST_CODE.DEFAULT, httpClientRequest, playlist))
                    .sorted((p1, p2) -> Long.compare(p2.getLikeCount(), p1.getLikeCount()))
                    .collect(Collectors.toList());

            if(CollectionUtils.isEmpty(playlists)){
                return CommonResponse.success(ErrorCode.COMMON_EMPTY_DATA);
            }
            return CommonResponse.success(playlists);
        }
        catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
        }
    }

    @Override
    public CommonResponse<List<PlaylistInfo>> playlistOrderByLikeCount() {

        Aggregation filterByRegDateAndSorting = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("curRegDate")
                        .gte(LocalDateTime.parse(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00.000", Locale.KOREA))))
                        .lte(LocalDateTime.parse(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'23:59:59.999", Locale.KOREA))))
                ),
                Aggregation.sort(Sort.Direction.DESC, "likeCount").and(Sort.Direction.DESC, "curRegDate")
        );

        try{
            List<PlaylistInfo> playlists = playlistRepository.playlistOrderByLikeCount(filterByRegDateAndSorting, "playlistWithPrevLikeCount")
                    .getMappedResults()
                    .stream()
                    .map(data -> imgRequestUtil.requestImgPathForBatch(httpClientRequest, data))
                    .limit(4)
                    .collect(Collectors.toList());

            if(CollectionUtils.isEmpty(playlists)){
                return CommonResponse.success(ErrorCode.COMMON_EMPTY_DATA);
            }
            return CommonResponse.success(playlists);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
        }
    }

    @Override
    public CommonResponse deleteMovie(DeleteMovie.Request request) {
        try{
            Playlist playlist = playlistRepository.findById(new ObjectId(request.getPlaylistId())).orElse(null);
            if(playlist == null){
                return CommonResponse.fail(ErrorCode.COMMON_ENTITY_NOT_FOUND);
            }
            playlist.setMovieIds(playlist.getMovieIds()
                    .stream()
                    .filter( id -> request.getMovieId() != id)
                    .collect(Collectors.toList()));

            playlistRepository.save(playlist);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
        }

        return CommonResponse.success(null, "영화 삭제에 성공하였습니다.");
    }

    @Override
    public CommonResponse likeAdd(PlaylistLikeAdd.Request request) {
        PlaylistLikeAdd.Response.ResponseBuilder playlist = PlaylistLikeAdd.Response.builder();
        try{
            ObjectId id = new ObjectId(request.getPlaylistId());
            Like like = likeRepository.findLikeByTargetIdAndMemberId(id, request.getId());
            if(like != null){
                likeRepository.deleteById(like.get_id());
                playlist.resultType("DELETE");
            } else{
                likeRepository.save(Like.builder()
                        .memberId(Math.toIntExact(request.getId()))
                        .targetId(id)
                        .likeType(request.getLikeType())
                        .build());

                playlist.resultType("INSERT");
            }

            Aggregation likeAggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("_id").is(id)),
                    Aggregation.lookup("like", "_id", "targetId", "likes")
            );

            PlaylistAggregationResult aggregationResult =  playlistRepository.playlistDetail(likeAggregation, "playlist");
            playlist.likeCount(aggregationResult.getLikes().size());
        } catch (Exception e){
            log.error(e.getMessage());
            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
        }
        return CommonResponse.success(playlist.build());

    }
}
