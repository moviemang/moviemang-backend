package com.moviemang.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.domain.MailCertificationDto;
import com.moviemang.datastore.entity.maria.Member;

@SuppressWarnings("rawtypes")
public interface MemberService {
	CommonResponse checkMailCertification(MailCertificationDto certificationDto);
    public Member regist(Member member);
    public CommonResponse checkEmail(String email);
    public CommonResponse checkNick(String nick);
    CommonResponse sendCertificationMail(String email) throws JsonProcessingException;
}
