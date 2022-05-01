package com.moviemang.playlist.domain;


import com.moviemang.coreutils.model.vo.CommonParam;
import lombok.Builder;
import lombok.Data;

public class Playlist {

    @Data
    public static class Request extends CommonParam {
        private Long id;
    }

    @Data
    @Builder
    public static class Response{
        private Long id;
        private String title;
    }
}
