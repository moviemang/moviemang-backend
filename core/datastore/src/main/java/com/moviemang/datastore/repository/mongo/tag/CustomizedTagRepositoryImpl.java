package com.moviemang.datastore.repository.mongo.tag;

import org.springframework.data.mongodb.core.MongoTemplate;

public class CustomizedTagRepositoryImpl implements CustomizedTagRepository {

    MongoTemplate mongoTemplate;

    public CustomizedTagRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }


}
