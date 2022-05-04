package com.moviemang.datastore.repository.mongo.playList;

import com.moviemang.datastore.domain.PlayListOrderByLikeDto;
import com.moviemang.datastore.entity.mongo.Playlist;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, ObjectId>, CustomizedPlayListRepository {

    PlayListOrderByLikeDto findPlayListDtoBy_id(ObjectId id);
}
