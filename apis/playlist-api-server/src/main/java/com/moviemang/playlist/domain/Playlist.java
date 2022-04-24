package com.moviemang.playlist.domain;


import lombok.Builder;
import lombok.Data;

public class Playlist {

    @Data
    public static class Request{
        private Long id;
    }

    @Data
    @Builder
    public static class Response{
        private Long id;
        private String title;
    }
}
