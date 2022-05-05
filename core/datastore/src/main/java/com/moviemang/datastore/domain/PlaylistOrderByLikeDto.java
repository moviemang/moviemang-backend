package com.moviemang.datastore.domain;

import lombok.*;
import org.bson.types.ObjectId;

import javax.persistence.Id;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistOrderByLikeDto {

    @Id
    private ObjectId _id;
    private String playlistTitle;
    private List<String> representativeImagePath;
    private List<String> tags;
    private List<Integer> movieIds;
    private int likeCount;

    @Builder
    public PlaylistOrderByLikeDto(ObjectId _id, String playlistTitle, List<String> representativeImagePath,
                                  List<String> tags, List<Integer> movieIds, int likeCount) {
        this._id = _id;
        this.playlistTitle = playlistTitle;
        this.representativeImagePath = representativeImagePath;
        this.tags = tags;
        this.movieIds = movieIds;
        this.likeCount = likeCount;
    }
}
