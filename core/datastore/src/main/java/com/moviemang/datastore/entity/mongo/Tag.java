package com.moviemang.datastore.entity.mongo;

import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document(collection = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {
    @Id
    private ObjectId _id;
    private String tagName;

    @Builder
    public Tag(ObjectId _id, String tagName) {
        this._id = _id;
        this.tagName = tagName;
    }
}
