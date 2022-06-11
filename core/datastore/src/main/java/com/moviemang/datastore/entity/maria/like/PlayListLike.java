package com.moviemang.datastore.entity.maria.like;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.entity.maria.playlist.Playlist;
import lombok.*;

import javax.persistence.*;

@Entity(name = "playlist_movie")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayListLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "playlist_like_id")
    @Column(name = "playlist_like_id")
    private Long playlistLikeId;

    @JsonProperty(value = "member_id")
    @Column(name = "member_id")
    private Long memberId;

    @JsonProperty(value = "playlist_movie_id")
    @Column(name = "playlist_movie_id")
    private String playlistMovieId;

    @JsonProperty(value = "target_id")
    @Column(name = "target_id")
    private Long targetId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public PlayListLike(Long playlistLikeId, Long memberId, String playlistMovieId, Long targetId) {
        this.playlistLikeId = playlistLikeId;
        this.memberId = memberId;
        this.playlistMovieId = playlistMovieId;
        this.targetId = targetId;
    }
}
