package com.moviemang.playlist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class Playlist {

    @AllArgsConstructor
    @Builder
    @Getter
    @ToString(callSuper = true)
    public static class Request extends CommonParam {

        @NotNull
        private String title;
        private List<TagInfo> tags;
        private String description;
        @JsonProperty("movie_ids")
        private List<Long> movieIds;
        private boolean display = false;

    }
}
