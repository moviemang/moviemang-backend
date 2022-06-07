package com.moviemang.datastore.repository.mongo.playlist;

import com.moviemang.datastore.dto.playlist.PlaylistAggregationResult;
import com.moviemang.datastore.dto.playlist.PlaylistWithPrevLikeCount;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public class CustomizedPlaylistRepositoryImpl implements CustomizedPlaylistRepository {

    MongoTemplate mongoTemplate;

    public CustomizedPlaylistRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public AggregationResults<PlaylistWithPrevLikeCount> playlistOrderByLikeCount(Aggregation aggregation, String collection) {
        return mongoTemplate.aggregate(aggregation, collection, PlaylistWithPrevLikeCount.class);
    }

    @Override
    public AggregationResults<PlaylistAggregationResult> lastestPlaylist(Aggregation aggregation, String collection) {
        return mongoTemplate.aggregate(aggregation, collection, PlaylistAggregationResult.class);
    }
}
