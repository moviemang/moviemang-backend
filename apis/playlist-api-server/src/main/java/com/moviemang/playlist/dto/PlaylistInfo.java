package com.moviemang.playlist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.bson.types.ObjectId;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class PlaylistInfo {
    private ObjectId id;
    private String title;
    private String nickname;
    private List<String> representativeImagePath;
    private List<TagInfo> tags;
    private boolean display;
    private Integer movieCount;
    private Long likeCount;
}
