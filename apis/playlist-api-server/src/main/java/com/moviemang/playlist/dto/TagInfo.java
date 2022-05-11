package com.moviemang.playlist.dto;

import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Builder
@ToString
public class TagInfo {
    private ObjectId id;
    private String name;
}
