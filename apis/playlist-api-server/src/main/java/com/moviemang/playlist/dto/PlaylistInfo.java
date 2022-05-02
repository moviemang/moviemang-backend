package com.moviemang.playlist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class PlaylistInfo {
    private Long id;
    private String title;
    private String nickname;
    private List<String> representativImagePath;
    private List<TagInfo> tags;
    private Integer movieCount;
    private Long likeCount;
}
