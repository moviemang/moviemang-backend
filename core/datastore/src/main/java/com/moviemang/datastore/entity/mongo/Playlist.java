package com.moviemang.datastore.entity.mongo;

import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@Document(collection = "playList")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist extends BaseTimeEntity {
    @Id
    private ObjectId _id;
    private String playListTitle;
    private int memberId;
    private String playListDescription;
    private List<Tag> tags;
    private List<Integer> movieIds;
    private String display;

    @Builder
    public Playlist(ObjectId _id, String playListTitle, int memberId, String playListDescription, List<Tag> tags, List<Integer> movieIds, String display) {
        this._id = _id;
        this.playListTitle = playListTitle;
        this.memberId = memberId;
        this.playListDescription = playListDescription;
        this.tags = tags;
        this.movieIds = movieIds;
        this.display = display;
    }
}
