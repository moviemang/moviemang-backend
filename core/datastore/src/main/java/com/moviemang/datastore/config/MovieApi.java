package com.moviemang.datastore.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieApi {

    private String API_KEY;
    private String BASE_URL;
    private String IMG_BASE_URL;

    public MovieApi(String api_key, String base_url, String img_base_url) {
        this.API_KEY = api_key;
        this.BASE_URL = base_url;
        this.IMG_BASE_URL = img_base_url;
    }
}
