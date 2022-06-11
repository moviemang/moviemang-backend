package com.moviemang.datastore.entity.maria.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity(name = "playlist_movie")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayListMovie extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "playlist_movie_id")
    @Column(name = "playlist_movie_id")
    private Long playlistMovieId;

    @Column(name = "playlist_id")
    private String playlistId;

    @Column(name = "movie_id")
    private Long movieId;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Builder
    public PlayListMovie(Long playlistMovieId, String playlistId, Long movieId) {
        this.playlistMovieId = playlistMovieId;
        this.playlistId = playlistId;
        this.movieId = movieId;
    }
}
