package com.moviemang.datastore.domain;

import com.moviemang.datastore.entity.mongo.Playlist;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistLikeJoin {

    private List<Playlist> playlist;
    private int likeCount;

    @Builder
    public PlaylistLikeJoin(int likeCount, List<Playlist> playlist) {
        this.likeCount = likeCount;
        this.playlist = playlist;
    }
}
