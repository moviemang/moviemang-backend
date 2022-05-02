package com.moviemang.datastore.repository.mongo.playList;

import com.moviemang.datastore.domain.PlayListOrderByLikeDto;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface CustomizedPlayListRepository {
    AggregationResults<PlayListOrderByLikeDto> playListLookupLike(Aggregation aggregation, String collection);
}
