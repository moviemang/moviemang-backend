package com.moviemang.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.dto.mail.MailCertificationDto;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.member.dto.DeletedMember;
import com.moviemang.member.dto.MemberInfo;
import com.moviemang.member.dto.MyPage;

import java.util.List;


@SuppressWarnings("rawtypes")
public interface MemberService {
	CommonResponse checkMailCertification(MailCertificationDto certificationDto);
    public CommonResponse regist(MemberJoinDto member);
    public CommonResponse checkEmail(String email);
    public CommonResponse checkNick(String nick);
    public CommonResponse deleteMember(DeletedMember deletedMember);
    CommonResponse sendCertificationMail(String email) throws JsonProcessingException;
    List<MemberInfo> selectAllMembers();
    CommonResponse myInfo(MyPage.Request request);
}
