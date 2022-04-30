package com.moviemang.datastore.repository.maria;

import com.moviemang.datastore.entity.maria.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedMemberRepository extends JpaRepository<Member,Long> {
}
