package com.moviemang.datastore.repository.maria;

import com.moviemang.datastore.entity.maria.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Integer countMemberByMemberEmail(String email);
    Integer countMemberByMemberName(String name);
    Optional<Member> findByMemberEmail(String email);
    Optional<Member> findByMemberId(Long memberId);

}
