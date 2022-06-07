package com.moviemang.playlist.config;

import com.google.common.collect.Maps;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import com.moviemang.datastore.config.MovieApiConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HttpClientConfig {

    MovieApiConfig movieApiConfig;

    public HttpClientConfig(MovieApiConfig movieApiConfig) {
        this.movieApiConfig = movieApiConfig;
    }

    @Bean
    public HttpClientRequest httpClientRequest(){
        HttpClientRequest httpRequest = new HttpClientRequest();
        Map<String, Object> param = Maps.newHashMap();
        param.put("api_key", movieApiConfig.getMovieApiProperties().getApiKey());
        httpRequest.setData(param);
        return httpRequest;
    }
}
