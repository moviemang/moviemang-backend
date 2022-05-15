package com.moviemang.datastore.domain;

import com.moviemang.datastore.entity.mongo.Like;
import com.moviemang.datastore.entity.mongo.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.persistence.Id;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistLikeJoin {

    @Id
    private ObjectId _id;
    private String playlistTitle;
    private Long memberId;
    private List<Tag> tags;
    private List<Integer> movieIds;
    private boolean display;
    private List<Like> likes;

    @Builder
    public PlaylistLikeJoin(ObjectId _id, String playlistTitle, Long memberId, List<Tag> tags, List<Integer> movieIds, boolean display, List<Like> likes) {
        this._id = _id;
        this.playlistTitle = playlistTitle;
        this.memberId = memberId;
        this.tags = tags;
        this.movieIds = movieIds;
        this.display = display;
        this.likes = likes;
    }
}
