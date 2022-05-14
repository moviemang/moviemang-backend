package com.moviemang.datastore.entity.mongo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieInfo {
    private Long movieId;
    private String imaPath;

    @Builder
    public MovieInfo(Long movieId, String imaPath) {
        this.movieId = movieId;
        this.imaPath = imaPath;
    }
}
