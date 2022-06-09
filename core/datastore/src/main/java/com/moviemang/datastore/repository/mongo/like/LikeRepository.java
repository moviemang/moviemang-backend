package com.moviemang.datastore.repository.mongo.like;

import com.moviemang.datastore.entity.mongo.Like;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, ObjectId>, CustomizedLikeRepository {

    Like findLikeByTargetIdAndMemberId(ObjectId targetId, long memberId);
}
