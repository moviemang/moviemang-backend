package com.moviemang.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.datastore.dto.mail.MailCertificationDto;
import com.moviemang.datastore.dto.member.EmailCheckDto;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.datastore.dto.member.NameCheckDto;
import com.moviemang.member.dto.DeletedMember;
import com.moviemang.member.dto.MyPage;
import com.moviemang.member.service.MemberService;
import com.moviemang.security.uitls.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
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
    public CommonResponse getEmailCheck(@RequestBody @Validated EmailCheckDto emailCheckDto){
        if(emailCheckDto.getEmail() == null) {
            return CommonResponse.fail(ErrorCode.COMMON_EMPTY_DATA);
        }
        return memberService.checkEmail(emailCheckDto.getEmail());
    }
    @PostMapping(path = "/nickcheck")
    public CommonResponse getNickCheck(@RequestBody NameCheckDto nameCheckDto){
        if(nameCheckDto.getNickname() == null) {
            return CommonResponse.fail(ErrorCode.COMMON_EMPTY_DATA);
        }
        return memberService.checkEmail(nameCheckDto.getNickname());
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

    @GetMapping(path = "/myInfo")
    public CommonResponse myInfo(HttpServletRequest httpServletRequest, MyPage.Request request){
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        return memberService.myInfo(request);
    }
}
