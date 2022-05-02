package com.moviemang.datastore.repository.maria;

import com.moviemang.datastore.entity.maria.DeletedMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedMemberRepository extends JpaRepository<DeletedMember,Long> {
}
