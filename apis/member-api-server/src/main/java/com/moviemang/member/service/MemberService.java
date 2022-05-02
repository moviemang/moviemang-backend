package com.moviemang.member.service;

import com.moviemang.coreutils.common.response.CommonResponse;

import com.moviemang.datastore.domain.MailCertificationDto;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.member.domain.DeletedMember;


@SuppressWarnings("rawtypes")
public interface MemberService {
	CommonResponse checkMailCertification(MailCertificationDto certificationDto);
    public CommonResponse regist(MemberJoinDto member);
    public CommonResponse checkEmail(String email);
    public CommonResponse checkNick(String nick);
    public CommonResponse deleteMember(DeletedMember deletedMember);
}
