package com.moviemang.playlist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.coreutils.model.vo.CommonParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class PlaylistDetail {
    @Data
    public static class Request extends CommonParam {
        @JsonProperty(value = "playlist_id")
        private String playlistId;
    }

    @Getter
    @Data
    @AllArgsConstructor
    @Builder
    public static class Response{
        private PlaylistInfo playlistInfo;
    }
}
