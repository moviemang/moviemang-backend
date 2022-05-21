package com.moviemang.datastore.repository.mongo.like;

import com.moviemang.datastore.dto.playlist.PlaylistAggregationResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public class CustomizedLikeRepositoryImpl implements CustomizedLikeRepository {

    MongoTemplate mongoTemplate;

    public CustomizedLikeRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public AggregationResults<PlaylistAggregationResult> findTop2ByMemberIdOrderByRegDateDesc(Aggregation likeAggregation, String collection) {
        return mongoTemplate.aggregate(likeAggregation, collection, PlaylistAggregationResult.class);
    }
}
