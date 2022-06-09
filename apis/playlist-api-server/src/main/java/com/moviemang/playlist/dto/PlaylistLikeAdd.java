package com.moviemang.playlist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.coreutils.model.vo.CommonParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class PlaylistLikeAdd {

    @Data
    public static class Request extends CommonParam {
        @JsonProperty(value = "playlist_id")
        private String PlaylistId;
        @JsonProperty(value = "like_type")
        private String likeType;
    }

    @Getter
    @Data
    @AllArgsConstructor
    @Builder
    public static class Response{
        private int likeCount;
        private String resultType;
    }
}
