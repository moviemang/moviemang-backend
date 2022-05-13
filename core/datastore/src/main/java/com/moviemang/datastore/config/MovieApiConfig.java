package com.moviemang.datastore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:movie/movieApi.yml", factory = YamlPropertySourceFactory.class)
public class MovieApiConfig {
    @Bean
    @ConfigurationProperties(prefix = "movie")
    public MovieApi getMovieApiProperties(){
        return new MovieApi();
    }

}
