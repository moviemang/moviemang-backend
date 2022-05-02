package com.moviemang.datastore.repository.mongo.playList;

import org.springframework.data.mongodb.core.MongoTemplate;

public class CustomizedPlayListRepositoryImpl implements CustomizedPlayListRepository {

    MongoTemplate mongoTemplate;

    public CustomizedPlayListRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }


}
