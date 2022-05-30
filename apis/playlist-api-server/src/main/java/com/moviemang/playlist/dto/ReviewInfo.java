package com.moviemang.playlist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@AllArgsConstructor
@Builder
public class ReviewInfo {
    private String id;
    private String title;
    private String reviewContent;
    private String imgPathUrl;
    private Long targetId;
    private boolean display;
    private LocalDateTime regDate;
    private LocalDateTime modTime;
}
