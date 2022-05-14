package com.moviemang.datastore.repository.mongo.playlist;

import com.moviemang.datastore.entity.mongo.Playlist;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, ObjectId>, CustomizedPlaylistRepository {

}
