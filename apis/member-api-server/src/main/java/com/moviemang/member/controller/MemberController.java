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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity postRegist(@RequestBody @Validated MemberJoinDto memberJoinDto){
        return memberService.regist(memberJoinDto);
    }

    @PostMapping(path = "/emailcheck")
    public ResponseEntity getEmailCheck(@RequestBody @Validated EmailCheckDto emailCheckDto){
        if(emailCheckDto.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponse.fail(ErrorCode.COMMON_EMPTY_DATA));
        }
        return memberService.checkEmail(emailCheckDto.getEmail());
    }
    @PostMapping(path = "/nickcheck")
    public ResponseEntity getNickCheck(@RequestBody @Validated NameCheckDto nameCheckDto){
        if(nameCheckDto.getNickname() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponse.fail(ErrorCode.COMMON_EMPTY_DATA));
        }
        return memberService.checkNick(nameCheckDto.getNickname());
    }

    @DeleteMapping
    public ResponseEntity deleteMember(HttpServletRequest httpServletRequest, DeletedMember deletedMember) {
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, deletedMember);
        return memberService.deleteMember(deletedMember);
    }
	@PostMapping(path = "/email/certification")
    public ResponseEntity checkCertification(@RequestBody MailCertificationDto certificationDto){
    	return memberService.checkMailCertification(certificationDto);
    }

    @PostMapping(path = "/email")
    public ResponseEntity sendMailCertification(@RequestBody String email) throws JsonProcessingException {
        return memberService.sendCertificationMail(email);
    }

    @GetMapping(path = "/myInfo")
    public ResponseEntity myInfo(HttpServletRequest httpServletRequest, MyPage.Request request){
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        return memberService.myInfo(request);
    }

    @PatchMapping(path = "/nickname")
    public ResponseEntity changeName(HttpServletRequest httpServletRequest, MyPage.Request request, @RequestBody String nickname) throws JsonProcessingException {
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        return memberService.changeName(request, nickname);
    }

    @PatchMapping(path = "/mailService")
    public ResponseEntity changeMailService(HttpServletRequest httpServletRequest, MyPage.Request request, @RequestBody String mailServiceUseYn) throws JsonProcessingException {
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        return memberService.changeMailService(request, mailServiceUseYn);
    }

    @PatchMapping(path = "/password")
    public ResponseEntity changePassword(HttpServletRequest httpServletRequest, MyPage.Request request, @RequestBody String password) throws JsonProcessingException {
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        return memberService.changePassword(request, password);
    }
}
