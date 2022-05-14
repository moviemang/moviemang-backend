package com.moviemang.datastore.repository.mongo.playlist;

import com.moviemang.datastore.domain.PlaylistLikeJoin;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface CustomizedPlaylistRepository {

    AggregationResults<PlaylistLikeJoin> playlistOrderByLike(Aggregation aggregation, String collection);
}
