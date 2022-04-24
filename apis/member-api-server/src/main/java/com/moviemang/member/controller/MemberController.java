package com.moviemang.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.domain.MailCertificationDto;
import com.moviemang.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/member", consumes = "application/json")
@CrossOrigin(origins = "*")
public class MemberController {

    private MemberService memberService;
    
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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
