package com.moviemang.member.controller;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.member.service.MemberService;
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

import com.moviemang.datastore.dto.MailCertificationDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "/member", consumes = "application/json")
@RestController

public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(path = "/join")
    public CommonResponse postRegist(@ModelAttribute @Validated MemberJoinDto memberJoinDto, Errors errors){

        return memberService.regist(memberJoinDto);

    }
    @GetMapping(path = "/emailcheck/{email}")
    public CommonResponse getEmailCheck(@PathVariable(value = "") String email){
        return memberService.checkEmail(email);
    }
    @GetMapping(path = "/nickcheck/{nick}")
    public CommonResponse getNickCheck(@PathVariable(value = "") String nick)
    {
        return memberService.checkNick(nick);
    }

    /**
     * 이메일 인증 시 인증번호 확인
     * @return {@link CommonResponse}
     */
	@PostMapping(path = "email/certification", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse checkCertification(@RequestBody MailCertificationDto certificationDto){
    	log.info("certificationDto : {}", certificationDto.toString());
    	return memberService.checkMailCertification(certificationDto);
    }

}
