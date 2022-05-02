package com.moviemang.datastore.repository.maria;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviemang.datastore.entity.maria.MailCertification;

@Transactional
public interface MailCertificationRepository extends JpaRepository<MailCertification, Integer>{
	
	MailCertification findTop1ByMemberEmailOrderByRegDateDesc(String memberEmail);
	
}
