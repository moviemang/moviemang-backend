package com.moviemang.datastore.repository.mongo.like;

import com.moviemang.datastore.domain.FilteredLikeDto;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface CustomizedLikeRepository {

    AggregationResults<FilteredLikeDto> filterByTypeAndGroupByTargetId(Aggregation likeAggregation, String collection);
}
