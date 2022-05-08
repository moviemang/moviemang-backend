package com.moviemang.datastore.domain;

import com.moviemang.datastore.entity.mongo.Tag;
import lombok.*;
import org.bson.types.ObjectId;

import javax.persistence.Id;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistOrderByLikeDto {

    @Id
    private ObjectId _id;
    private String playlistTitle;
    private String memberName;
    private List<String> representativeImagePath;
    private List<Tag> tags;
    private int moiveCount;
    private int likeCount;

    @Builder
    public PlaylistOrderByLikeDto(ObjectId _id, String playlistTitle, String memberName, List<String> representativeImagePath,
                                  List<Tag> tags, int movieCount, int likeCount) {
        this._id = _id;
        this.playlistTitle = playlistTitle;
        this.memberName = memberName;
        this.representativeImagePath = representativeImagePath;
        this.tags = tags;
        this.moiveCount = movieCount;
        this.likeCount = likeCount;
    }
}
