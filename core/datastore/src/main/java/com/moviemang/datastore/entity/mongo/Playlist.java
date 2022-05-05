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
    private String playlistTitle;
    private Long memberId;
    private String playlistDescription;
    private List<Tag> tags;
    private List<Integer> movieIds;
    private boolean display;

    @Builder
    public Playlist(ObjectId _id, String playlistTitle, Long memberId, String playListDescription, List<Tag> tags, List<Integer> movieIds, boolean display) {
        this._id = _id;
        this.playlistTitle = playlistTitle;
        this.memberId = memberId;
        this.playlistDescription = playlistDescription;
        this.tags = tags;
        this.movieIds = movieIds;
        this.display = display;
    }
}
