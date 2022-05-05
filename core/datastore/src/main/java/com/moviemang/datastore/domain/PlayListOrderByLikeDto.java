package com.moviemang.datastore.domain;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;


@Getter
@Setter
@ToString
@Document(collection = "playListOrderByLikeDto")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayListOrderByLikeDto {

    @Id
    private ObjectId _id;
    private String playListTitle;
    private List<String> representativeImagePath;
    private List<String> tags;
    private List<Integer> movieIds;
    private int likeCount;

    @Builder
    public PlayListOrderByLikeDto(ObjectId _id, String playListTitle, List<String> representativeImagePath,
                                  List<String> tags, List<Integer> movieIds, int likeCount) {
        this._id = _id;
        this.playListTitle = playListTitle;
        this.representativeImagePath = representativeImagePath;
        this.tags = tags;
        this.movieIds = movieIds;
        this.likeCount = likeCount;
    }
}