package com.moviemang.datastore.entity.mongo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@ToString
@Document(collection = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends MongoBaseTimeEntity {
    @Id
    private ObjectId _id;
    private String reviewContent;
    private Long memberId;
    private Long reviewTargetId;
    private String reviewTargetType;
    private boolean display;

    @Builder
    public Review(ObjectId _id, String reviewContent, Long memberId
            , Long reviewTargetId, String reviewTargetType, boolean display) {
        this._id = _id;
        this.reviewContent = reviewContent;
        this.memberId = memberId;
        this.reviewTargetId = reviewTargetId;
        this.reviewTargetType = reviewTargetType;
        this.display = display;
    }
}
