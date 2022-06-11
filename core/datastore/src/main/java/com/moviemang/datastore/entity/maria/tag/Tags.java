package com.moviemang.datastore.entity.maria.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Entity(name = "tags")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "tag_id")
    @Column(name = "tag_id")
    private Long tagId;

    @JsonProperty(value = "tag_name")
    @Column(name = "tag_name")
    private String tagName;

    @JsonBackReference(value = "playlist_tag")
    @ManyToOne
    private PlayListTag playListTag;

    @Builder
    public Tags(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
