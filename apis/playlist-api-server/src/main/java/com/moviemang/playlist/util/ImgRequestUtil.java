package com.moviemang.playlist.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.exception.MovieApiException;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.utils.httpclient.HttpClient;
import com.moviemang.datastore.config.MovieApiConfig;
import com.moviemang.datastore.dto.playlist.PlaylistAggregationResult;
import com.moviemang.datastore.dto.playlist.PlaylistWithPrevLikeCount;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.playlist.dto.PlaylistInfo;
import com.moviemang.playlist.mapper.PlaylistMapperImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static javafx.scene.CacheHint.DEFAULT;

@Slf4j
@Component
public class ImgRequestUtil {
    private MemberRepository memberRepository;
    private ObjectMapper objectMapper;
    private PlaylistMapperImpl playlistMapperImpl;
    private MovieApiConfig movieApiConfig;

    public enum REQUEST_CODE{
        DEFAULT, DETAIL
    }

    @Autowired
    public ImgRequestUtil(MemberRepository memberRepository, ObjectMapper objectMapper,
                          PlaylistMapperImpl playlistMapperImpl, MovieApiConfig movieApiConfig) {
        this.memberRepository = memberRepository;
        this.objectMapper = objectMapper;
        this.playlistMapperImpl = playlistMapperImpl;
        this.movieApiConfig = movieApiConfig;
    }



    public PlaylistInfo requestImgPath(REQUEST_CODE code, HttpClientRequest request, PlaylistAggregationResult aggregationResult){
        List<String> imgPathList = new ArrayList<String>();
        List<Integer> movieIds = aggregationResult.getMovieIds();

        if(code.equals(REQUEST_CODE.DEFAULT)){
            Collections.shuffle(movieIds);
        }

        int idx = 0;
        for(int movieId : movieIds){
            if(idx == 6){
                break;
            }
            request.setUrl(String.format("%s/movie/%d/images", movieApiConfig.getMovieApiProperties().getBaseUrl(), movieId));
            try {
                Map<String, Object> response = objectMapper.readValue(HttpClient.get(request), HashMap.class);
                if(response.get("status_code") != null){
                    if(StringUtils.equals(response.get("status_code").toString(), "7")){
                        throw new MovieApiException(response.get("status_massage").toString(), ErrorCode.INVALID_API_KEY);
                    }
                    continue;
                }
                List<Map<String, Object>> posterData = (List<Map<String, Object>>) response.get("posters");
                imgPathList.add(movieApiConfig.getMovieApiProperties().getImgBaseUrl() + posterData.get(0).get("file_path").toString());
                idx++;
            } catch (Exception e) {
                // Movie API ????????? ?????? ???????????? ?????? ????????? ???????????? ????????? ????????? ????????? ?????? ???
                log.error("movie not found error => {}", movieId);
                throw new BaseException(ErrorCode.NOT_FOUND_MOVIE);
            }
        }
        return PlaylistInfo.builder()
                .id(aggregationResult.get_id().toHexString())
                .title(aggregationResult.getPlaylistTitle())
                .nickname(memberRepository.findByMemberId(aggregationResult.getMemberId())
                        .orElseThrow(() -> new BaseException(aggregationResult.getMemberId().toString(), ErrorCode.USER_NOT_FOUND))
                        .getMemberName())
                .description(aggregationResult.getPlaylistDescription())
                .representativeImagePath(imgPathList)
                .tags(playlistMapperImpl.tagInfos(aggregationResult.getTags()))
                .movieCount(aggregationResult.getMovieIds().size())
                .likeCount(aggregationResult.getLikes().size())
                .display(true)
                .regDate(aggregationResult.getRegDate())
                .modDate(aggregationResult.getModDate())
                .build();

    }

    public PlaylistInfo requestImgPathForBatch(HttpClientRequest request, PlaylistWithPrevLikeCount item) {
        List<String> imgPathList = new ArrayList<String>();
        List<Integer> movieIds = item.getPlaylist().getMovieIds();
        Collections.shuffle(movieIds);
        int idx = 0;
        for(int movieId : movieIds){
            if(idx == 6){
                break;
            }
            request.setUrl(String.format("%s/movie/%d/images", movieApiConfig.getMovieApiProperties().getBaseUrl(), movieId));
            try {
                Map<String, Object> response = objectMapper.readValue(HttpClient.get(request), HashMap.class);
                if(response.get("status_code") != null){
                    if(StringUtils.equals(response.get("status_code").toString(), "7")){
                        throw new MovieApiException(response.get("status_massage").toString(), ErrorCode.INVALID_API_KEY);
                    }
                    continue;
                }
                List<Map<String, Object>> posterData = (List<Map<String, Object>>) response.get("posters");
                imgPathList.add(movieApiConfig.getMovieApiProperties().getImgBaseUrl() + posterData.get(0).get("file_path").toString());
                idx++;
            } catch (Exception e) {
                // Movie API ????????? ?????? ???????????? ?????? ????????? ???????????? ????????? ????????? ????????? ?????? ???
                log.error("movie not found error => {}", movieId);
                throw new BaseException(ErrorCode.NOT_FOUND_MOVIE);
            }
        }
        return PlaylistInfo.builder()
                .id(item.getPlaylist().get_id().toHexString())
                .title(item.getPlaylist().getPlaylistTitle())
                .nickname(memberRepository.findByMemberId(item.getPlaylist().getMemberId())
                        .orElseThrow(() -> new BaseException(item.getPlaylist().getMemberId().toString(), ErrorCode.USER_NOT_FOUND))
                        .getMemberName())
                .description(item.getPlaylist().getPlaylistDescription())
                .representativeImagePath(imgPathList)
                .tags(playlistMapperImpl.tagInfos(item.getPlaylist().getTags()))
                .movieCount(movieIds.size())
                .likeCount(item.getLikeCount())
                .display(true)
                .regDate(item.getPlaylist().getRegDate())
                .modDate(item.getPlaylist().getModDate())
                .build();
    }

}

