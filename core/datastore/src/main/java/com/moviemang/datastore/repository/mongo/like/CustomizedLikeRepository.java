package com.moviemang.datastore.repository.mongo.like;

import com.moviemang.datastore.dto.playlist.PlaylistAggregationResult;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface CustomizedLikeRepository {

    AggregationResults<PlaylistAggregationResult> findTop2ByMemberIdOrderByRegDateDesc(Aggregation likeAggregation, String collection);
}
