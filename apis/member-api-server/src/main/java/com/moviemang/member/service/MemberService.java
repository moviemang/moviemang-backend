package com.moviemang.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviemang.datastore.dto.mail.MailCertificationDto;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.member.dto.DeletedMember;
import com.moviemang.member.dto.MemberInfo;
import com.moviemang.member.dto.MyPage;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SuppressWarnings("rawtypes")
public interface MemberService {
	ResponseEntity checkMailCertification(MailCertificationDto certificationDto);
    ResponseEntity regist(MemberJoinDto member);
    ResponseEntity checkEmail(String email);
    ResponseEntity checkNick(String nick);
    ResponseEntity deleteMember(DeletedMember deletedMember);
    ResponseEntity sendCertificationMail(String email) throws JsonProcessingException;
    List<MemberInfo> selectAllMembers();
    ResponseEntity myInfo(MyPage.Request request);
    ResponseEntity changeName(MyPage.Request request, String nickname) throws JsonProcessingException;
    ResponseEntity changeMailService(MyPage.Request request, String mailServiceUseYn) throws JsonProcessingException;
    ResponseEntity changePassword(MyPage.Request request, String password) throws JsonProcessingException;
}
