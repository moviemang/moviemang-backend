package com.moviemang.datastore.repository.mongo.review;

import com.moviemang.datastore.entity.mongo.Review;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId>, CustomizedReviewRepository {

    List<Review> findByMemberIdOrderByRegDate(long memberId, Pageable pageRequest);
}
