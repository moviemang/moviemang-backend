package com.moviemang.playlist.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieDetail {
    String movieTitle;
    String moviePosterImgPath;

    @Builder
    public MovieDetail(String movieTitle, String moviePosterImgPath) {
        this.movieTitle = movieTitle;
        this.moviePosterImgPath = moviePosterImgPath;
    }
}
