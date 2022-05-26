package com.moviemang.datastore.entity.mongo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@ToString
@Document(collection = "prevLikes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrevLikes {

    @Id
    private ObjectId _id;
    private ObjectId targetId;
    private LocalDateTime regDate;

    @Builder
    public PrevLikes(ObjectId _id, ObjectId targetId, LocalDateTime regDate) {
        this._id = _id;
        this.targetId = targetId;
        this.regDate = regDate;
    }
}
