package com.moviemang.playlist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class DeleteMovie {

    @AllArgsConstructor
    @Builder
    @Getter
    @ToString(callSuper = true)
    public static class Request extends CommonParam {

        @NotNull
        @JsonProperty("playlist_id")
        private String playlistId;

        @NotNull
        @JsonProperty("movie_id")
        private int movieId;
    }
}
