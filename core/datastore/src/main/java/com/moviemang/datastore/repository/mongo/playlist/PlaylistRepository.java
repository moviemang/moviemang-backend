package com.moviemang.datastore.repository.mongo.playlist;

import com.moviemang.datastore.entity.mongo.PlayListOrderByLikeDto;
import com.moviemang.datastore.entity.mongo.Playlist;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, ObjectId>, CustomizedPlaylistRepository {

    List<Playlist> findTop2ByMemberIdOrderByRegDateDesc(long l);
    List<Playlist> findByMemberId(Long memberId, Pageable pageRequest);

}
