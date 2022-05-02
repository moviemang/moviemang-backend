package com.moviemang.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.dto.MailCertificationDto;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.member.dto.DeletedMember;
import com.moviemang.member.service.MemberService;
import com.moviemang.security.uitls.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(path = "/emailcheck/{email}")
    public CommonResponse getEmailCheck(@PathVariable(value = "email") String email){
        return memberService.checkEmail(email);
    }
    @GetMapping(path = "/nickcheck/{nick}")
    public CommonResponse getNickCheck(@PathVariable String nick)
    {
        return memberService.checkNick(nick);
    }

    @DeleteMapping
    public CommonResponse deleteMember(HttpServletRequest httpServletRequest, DeletedMember deletedMember) {
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, deletedMember);
        return memberService.deleteMember(deletedMember);
    }
	@PostMapping(path = "/certificationCheck")
    public CommonResponse checkCertification(@RequestBody MailCertificationDto certificationDto){
    	return memberService.checkMailCertification(certificationDto);
    }

    @PostMapping(path = "/certificationSend")
    public CommonResponse sendMailCertification(@RequestBody String email) throws JsonProcessingException {
        return memberService.sendCertificationMail(email);
    }
}
