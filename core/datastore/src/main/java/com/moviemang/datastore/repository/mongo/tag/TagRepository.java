package com.moviemang.datastore.repository.mongo.tag;

import com.moviemang.datastore.entity.mongo.Tag;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<Tag, ObjectId>, CustomizedTagRepository {

}
