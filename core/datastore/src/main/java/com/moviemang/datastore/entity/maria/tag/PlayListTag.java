package com.moviemang.datastore.entity.maria.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import com.moviemang.datastore.entity.maria.playlist.Playlist;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "playlist_tag")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayListTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "playlist_tag_id")
    @Column(name = "playlist_tag_id")
    private Long playlistTagId;

    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "tag_id")
    private Long tagId;

    @JsonManagedReference(value = "playlist_tag")
    @OneToMany
    @JoinColumn(name = "tag_id")
    private List<Tags> tags;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Playlist playlists;

    @Builder
    public PlayListTag(Long playlistTagId, Long playlistId, Long tagId) {
        this.playlistTagId = playlistTagId;
        this.playlistId = playlistId;
        this.tagId = tagId;
    }
}
