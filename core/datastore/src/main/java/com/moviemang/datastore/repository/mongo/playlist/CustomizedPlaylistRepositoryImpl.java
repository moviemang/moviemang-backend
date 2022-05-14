package com.moviemang.datastore.repository.mongo.playlist;

import org.springframework.data.mongodb.core.MongoTemplate;

public class CustomizedPlaylistRepositoryImpl implements CustomizedPlaylistRepository {

    MongoTemplate mongoTemplate;

    public CustomizedPlaylistRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

}
