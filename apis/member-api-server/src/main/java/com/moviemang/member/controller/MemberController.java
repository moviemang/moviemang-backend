package com.moviemang.member.controller;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "/member")
@RestController
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(path = "/join", consumes = "application/json")
    public CommonResponse postRegist(@ModelAttribute @Validated Member member, Errors errors){


        Member newbie = memberService.regist(member);
        if(newbie!=null){
            return CommonResponse.builder()
                    .result(CommonResponse.Result.SUCCESS)
                    .status(HttpStatus.CREATED)
                    .build();
        }else{

            return CommonResponse.fail(ErrorCode.COMMON_ILLEGAL_STATUS);
        }
    }
    @GetMapping(path = "/emailcheck/{email}")
    public CommonResponse getEmailCheck(@PathVariable(value = "") String email){
        return memberService.checkEmail(email);
    }
    @GetMapping(path = "/nickcheck/{nick}")
    public CommonResponse getNickCheck(@PathVariable(value = "") String nick){
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

    @DeleteMapping(path = "/{member_id}")
    public CommonResponse deleteMember(@PathVariable("member_id") Long member_id){
        System.out.println("[Controller] delete member id : " + member_id);
        return memberService.deleteMember(member_id);
    }

}
