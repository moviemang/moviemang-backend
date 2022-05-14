package com.moviemang.datastore.repository.mongo.like;

import com.moviemang.datastore.domain.PlaylistLikeJoin;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface CustomizedLikeRepository {

    AggregationResults<PlaylistLikeJoin> findTop2ByMemberIdOrderByRegDateDesc(Aggregation likeAggregation, String collection);
}
