package com.moviemang.playlist.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.moviemang.coreutils.common.exception.MovieApiException;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.coreutils.utils.httpclient.HttpClient;
import com.moviemang.datastore.config.MovieApiConfig;
import com.moviemang.playlist.dto.MovieDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MovieApiRequestUtil {

    private MovieApiConfig movieApiConfig;
    private ObjectMapper objectMapper;

    @Autowired
    public MovieApiRequestUtil(MovieApiConfig movieApiConfig, ObjectMapper objectMapper) {
        this.movieApiConfig = movieApiConfig;
        this.objectMapper = objectMapper;
    }

    public MovieDetail requestMovieDetail(Long movieId){
        MovieDetail.MovieDetailBuilder movieDetail = MovieDetail.builder();
        HttpClientRequest request = new HttpClientRequest();
        Map<String, Object> param = Maps.newHashMap();
        param.put("api_key", movieApiConfig.getMovieApiProperties().getApiKey());

        request.setData(param);
        request.setUrl(String.format("%s/movie/%d", movieApiConfig.getMovieApiProperties().getBaseUrl(), movieId));

        try{
            Map<String, Object> response = objectMapper.readValue(HttpClient.get(request), HashMap.class);
            if(response.get("status_code") != null){
                if(StringUtils.equals(response.get("status_code").toString(), "7")){
                    throw new MovieApiException(response.get("status_massage").toString(), ErrorCode.INVALID_API_KEY);
                }
                else{
                    throw new MovieApiException(response.get("status_massage").toString(), ErrorCode.NOT_FOUND_MOVIE);
                }
            }
            String posterUrl = response.get("backdrop_path").toString();
            if(StringUtils.isEmpty(posterUrl)){
                log.info("해당 영화에 대한 이미지가 존재하지 않습니다.");
            }
            String imgUrl = movieApiConfig.getMovieApiProperties().getImgBaseUrl() + posterUrl;
            movieDetail.movieTitle(response.get("original_title").toString());
            movieDetail.moviePosterImgPath(imgUrl);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new MovieApiException(ErrorCode.MOVIE_API_REQUEST_ERROR);
        }
        return movieDetail.build();
    }

}
