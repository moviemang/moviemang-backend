package com.moviemang.member.service;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.dto.MailCertificationDto;
import com.moviemang.datastore.dto.member.MemberJoinDto;

@SuppressWarnings("rawtypes")
public interface MemberService {
	CommonResponse checkMailCertification(MailCertificationDto certificationDto);
    public CommonResponse regist(MemberJoinDto member);
    public CommonResponse checkEmail(String email);
    public CommonResponse checkNick(String nick);
}
