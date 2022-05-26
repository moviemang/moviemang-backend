package com.moviemang.datastore.repository.mongo.playlist;

import com.moviemang.datastore.dto.playlist.PlaylistWithPrevLikeCount;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface CustomizedPlaylistRepository {

    AggregationResults<PlaylistWithPrevLikeCount> playlistOrderByLikeCount(Aggregation aggregation, String collection);

}
