package com.moviemang.datastore.repository.mongo.review;

import org.springframework.data.mongodb.core.MongoTemplate;

public class CustomizedReviewRepositoryImpl implements CustomizedReviewRepository {

    MongoTemplate mongoTemplate;

    public CustomizedReviewRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

}
