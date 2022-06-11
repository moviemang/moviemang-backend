package com.moviemang.datastore.entity.maria.playlist;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.entity.maria.like.PlayListLike;
import com.moviemang.datastore.entity.maria.tag.PlayListTag;
import com.moviemang.datastore.entity.maria.tag.Tags;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Entity(name = "playlist")
@Getter
@Setter
@Document(collection = "playlist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "title")
    private String title;

    @Column(name = "descrption")
    private String description;

    @Column(name = "tag_id")
    private Long tagId;

    private boolean display;

    @OneToMany
    @JoinColumn(name = "tag_id")
    private List<Tags> tags;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany //(targetEntity = PlayListMovie.class)
    @JoinColumn(name = "playlist_movie_id")
    private List<PlayListMovie> movieIds;

    @JsonManagedReference
    @OneToMany
    @JoinColumn(name = "playlist_tag_id")
    private List<PlayListTag> playListTags;

    @JsonManagedReference
    @OneToMany
    private List<PlayListLike> playListLikes;

    @Builder
    public Playlist(Long playlistId, Long memberId, String title, String description, Long tagId
            , boolean display, List<Tags> tags, Member member) {
        this.playlistId = playlistId;
        this.memberId = memberId;
        this.title = title;
        this.description = description;
        this.tagId = tagId;
        this.display = display;
        this.tags = tags;
        this.member = member;
    }
}
