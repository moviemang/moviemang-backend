package com.moviemang.datastore.domain;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@ToString
@Document(collection = "FilteredLikeDto")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilteredLikeDto {

    @Id
    private ObjectId _id;
    private int likeCount;

    @Builder
    public FilteredLikeDto(ObjectId _id,int likeCount) {
        this._id = _id;
        this.likeCount = likeCount;
    }
}
