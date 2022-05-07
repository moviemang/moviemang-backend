package com.moviemang.playlist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MovieApi {

    private final String API_KEY;
    private final String BASE_URL;
    private final String IMG_BASE_URL;

    public MovieApi(@Value("${movie.api-key}") String API_KEY, @Value("${movie.base-url}") String BASE_URL,
                    @Value("${movie.img-url}") String IMG_BASE_URL){
        this.API_KEY = API_KEY;
        this.BASE_URL = BASE_URL;
        this.IMG_BASE_URL = IMG_BASE_URL;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getIMG_BASE_URL() {
        return IMG_BASE_URL;
    }
}
