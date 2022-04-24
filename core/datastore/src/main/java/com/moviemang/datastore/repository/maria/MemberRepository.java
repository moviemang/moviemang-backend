package com.moviemang.datastore.repository.maria;

import com.moviemang.datastore.entity.maria.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member,Long> {
}
