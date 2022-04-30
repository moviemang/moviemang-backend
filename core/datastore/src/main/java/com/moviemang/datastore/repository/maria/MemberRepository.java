package com.moviemang.datastore.repository.maria;

import com.moviemang.datastore.entity.maria.Member;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Integer countMemberByMemberEmail(String email);
    Integer countMemberByMemberName(String name);
    Optional<Member> findByMemberEmail(String email);

}
