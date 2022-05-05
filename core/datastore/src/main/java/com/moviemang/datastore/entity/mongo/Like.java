package com.moviemang.datastore.entity.mongo;

import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@ToString
@Document(collection = "like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseTimeEntity {
    @Id
    private ObjectId _id;
    private int memberId;
    private ObjectId targetId;
    private String likeType;

    @Builder
    public Like(ObjectId _id, int memberId, ObjectId targetId, String likeType) {
        this._id = _id;
        this.memberId = memberId;
        this.targetId = targetId;
        this.likeType = likeType;
    }
}
