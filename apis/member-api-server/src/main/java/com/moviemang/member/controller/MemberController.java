package com.moviemang.member.controller;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.member.domain.DeletedMember;
import com.moviemang.member.service.MemberService;
import com.moviemang.security.uitls.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.moviemang.datastore.dto.MailCertificationDto;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

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
    // Post로 Body에 넣어서  보내기
    @GetMapping(path = "/emailcheck/{email}")
    public CommonResponse getEmailCheck(@PathVariable(value = "email") String email){
        log.info("들어옴 ㅋㅋㅋㅋ");
        return memberService.checkEmail(email);
    }
    // Post로 Body에 넣어서  보내기
    @GetMapping(path = "/nickcheck/{nick}")
    public CommonResponse getNickCheck(@PathVariable String nick)
    {
        return memberService.checkNick(nick);
    }

//    /**
//     * 이메일 인증 시 인증번호 확인
//     * @return {@link CommonResponse}
//     */
//	@PostMapping(path = "email/certification", produces = MediaType.APPLICATION_JSON_VALUE)
//    public CommonResponse checkCertification(@RequestBody MailCertificationDto certificationDto){
//    	log.info("certificationDto : {}", certificationDto.toString());
//    	return memberService.checkMailCertification(certificationDto);
//    }

    @DeleteMapping
    public CommonResponse deleteMember(HttpServletRequest httpServletRequest, DeletedMember deletedMember){
//        System.out.println("[Controller] delete member id : " + deletedMember);
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, deletedMember);
        return memberService.deleteMember(deletedMember);
    }

}
