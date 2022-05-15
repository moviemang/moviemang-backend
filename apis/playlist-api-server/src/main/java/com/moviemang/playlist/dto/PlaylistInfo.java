package com.moviemang.playlist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@AllArgsConstructor
@Builder
public class PlaylistInfo {
    private String id;
    private String title;
    private String description;
    private String nickname;
    private List<String> representativeImagePath;
    private List<TagInfo> tags;
    private boolean display;
    private int movieCount;
    private long likeCount;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
