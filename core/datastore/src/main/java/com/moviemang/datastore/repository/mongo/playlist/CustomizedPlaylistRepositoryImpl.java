package com.moviemang.datastore.repository.mongo.playlist;

import com.moviemang.datastore.domain.PlaylistLikeJoin;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public class CustomizedPlaylistRepositoryImpl implements CustomizedPlaylistRepository {

    MongoTemplate mongoTemplate;

    public CustomizedPlaylistRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public AggregationResults<PlaylistLikeJoin> playlistOrderByLike(Aggregation aggregation, String collection) {
        return mongoTemplate.aggregate(aggregation, collection, PlaylistLikeJoin.class);
    }
}
