package com.moviemang.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.dto.MailCertificationDto;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.member.domain.DeletedMember;
import com.moviemang.member.service.MemberService;
import com.moviemang.security.uitls.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "/member")
@RestController
public class MemberController {

    private MemberService memberService;
    private AuthenticationUtil authenticationUtil;

    @Autowired
    public MemberController(MemberService memberService, AuthenticationUtil authenticationUtil) {
        this.memberService = memberService;
        this.authenticationUtil = authenticationUtil;
    }

    @PostMapping(path = "/join")
    public CommonResponse postRegist(@RequestBody @Validated MemberJoinDto memberJoinDto){
        return memberService.regist(memberJoinDto);
    }

    @PostMapping(path = "/emailcheck")
    public CommonResponse getEmailCheck(@RequestBody Map<String,String> email){
        return memberService.checkEmail(email.get("email"));
    }
    @PostMapping(path = "/nickcheck")
    public CommonResponse getNickCheck(@RequestBody Map<String,String> nick){
        return memberService.checkNick(nick.get("nick"));
    }

    @DeleteMapping
    public CommonResponse deleteMember(HttpServletRequest httpServletRequest, DeletedMember deletedMember) {
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, deletedMember);
        return memberService.deleteMember(deletedMember);
    }
	@PostMapping(path = "/email/certification")
    public CommonResponse checkCertification(@RequestBody MailCertificationDto certificationDto){
    	return memberService.checkMailCertification(certificationDto);
    }

    @PostMapping(path = "/email")
    public CommonResponse sendMailCertification(@RequestBody String email) throws JsonProcessingException {
        return memberService.sendCertificationMail(email);
    }
}
