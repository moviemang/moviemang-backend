package com.moviemang.datastore.repository.mongo.like;

import com.moviemang.datastore.domain.FilteredLikeDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public class CustomizedLikeRepositoryImpl implements CustomizedLikeRepository {

    MongoTemplate mongoTemplate;

    public CustomizedLikeRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public AggregationResults<FilteredLikeDto> filterByTypeAndGroupByTargetId(Aggregation likeAggregation, String collection) {
        return mongoTemplate.aggregate(likeAggregation, collection, FilteredLikeDto.class);
    }
}
