package com.moviemang.datastore.repository.mongo.like;

import org.springframework.data.mongodb.core.MongoTemplate;

public class CustomizedLikeRepositoryImpl implements CustomizedLikeRepository {

    MongoTemplate mongoTemplate;

    public CustomizedLikeRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }


}
