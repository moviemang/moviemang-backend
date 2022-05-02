package com.moviemang.member.service;

import com.moviemang.datastore.entity.maria.MailServiceUser;
import com.moviemang.datastore.repository.maria.MailUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor=Exception.class)
@Service
@Slf4j
public class MailUserServiceImpl implements MailUserService{

	private MailUserRepository mailRepo;

	@Autowired
	public MailUserServiceImpl(MailUserRepository mailRepo){
		this.mailRepo = mailRepo;
	}
	@Override
	public MailServiceUser memberJoin(MailServiceUser mailServiceUser) {
		log.info("mailServiceUser   : "+mailServiceUser.toString());
		return mailRepo.save(mailServiceUser);
	}
}
