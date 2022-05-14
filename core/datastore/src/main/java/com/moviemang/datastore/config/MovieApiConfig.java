package com.moviemang.datastore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieApiConfig {
    @Bean
    public MovieApi MovieApi(@Value("${movie.api-key}") String API_KEY, @Value("${movie.base-url}") String BASE_URL,
                             @Value("${movie.img-url}") String IMG_BASE_URL){
        return new MovieApi(API_KEY, BASE_URL, IMG_BASE_URL);
    }

}
