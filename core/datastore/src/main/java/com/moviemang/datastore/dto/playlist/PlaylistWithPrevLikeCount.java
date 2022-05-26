package com.moviemang.datastore.dto.playlist;

import com.moviemang.datastore.entity.mongo.Playlist;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Document(collection = "playlistWithPrevLikeCount")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistWithPrevLikeCount {

    @Id
    private ObjectId _id;
    private Playlist playlist;
    private int likeCount;
    private LocalDateTime curRegDate;

    @Builder
    public PlaylistWithPrevLikeCount(ObjectId _id, Playlist playlist, int likeCount, LocalDateTime curRegDate) {
        this._id = _id;
        this.playlist = playlist;
        this.likeCount = likeCount;
        this.curRegDate = curRegDate;
    }
}
