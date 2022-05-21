package com.moviemang.datastore.dto.playlist;

import com.moviemang.datastore.entity.mongo.Like;
import com.moviemang.datastore.entity.mongo.Tag;
import lombok.*;
import org.bson.types.ObjectId;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

/**
 * aggregation 결과로 공통적으로 쓰일 DTO 객체
 */
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistAggregationResult {

    @Id
    private ObjectId _id;
    private String playlistTitle;
    private String playlistDescription;
    private Long memberId;
    private List<Tag> tags;
    private List<Integer> movieIds;
    private boolean display;
    private List<Like> likes;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Builder
    public PlaylistAggregationResult(ObjectId _id, String playlistTitle, String playlistDescription, Long memberId, List<Tag> tags,
                                     List<Integer> movieIds, boolean display, List<Like> likes, LocalDateTime regDate, LocalDateTime modDate) {
        this._id = _id;
        this.playlistTitle = playlistTitle;
        this.playlistDescription = playlistDescription;
        this.memberId = memberId;
        this.tags = tags;
        this.movieIds = movieIds;
        this.display = display;
        this.likes = likes;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
