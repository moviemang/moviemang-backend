package com.moviemang.datastore.repository.mongo.playList;

import com.moviemang.datastore.entity.mongo.Playlist;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, ObjectId>, CustomizedPlayListRepository {

    List<Playlist> findByMemberId(Long memberId, Pageable pageable);
}
