package com.moviemang.datastore.repository.mongo.playlist;

import com.moviemang.datastore.dto.playlist.PlaylistAggregationResult;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface CustomizedPlaylistRepository {

    AggregationResults<PlaylistAggregationResult> playlistOrderByLike(Aggregation aggregation, String collection);
}
