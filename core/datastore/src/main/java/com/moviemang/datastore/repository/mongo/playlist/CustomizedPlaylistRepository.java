package com.moviemang.datastore.repository.mongo.playlist;

import com.moviemang.datastore.domain.PlaylistOrderByLikeDto;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface CustomizedPlaylistRepository {
    AggregationResults<PlaylistOrderByLikeDto> playlistLookupLike(Aggregation aggregation, String collection);
}
