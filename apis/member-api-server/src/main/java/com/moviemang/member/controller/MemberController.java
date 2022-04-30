package com.moviemang.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.datastore.domain.MailCertificationDto;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping(path = "/certificationCheck")
    public CommonResponse checkCertification(@RequestBody MailCertificationDto certificationDto){
    	return memberService.checkMailCertification(certificationDto);
    }

    @PostMapping(path = "/certificationSend")
    public CommonResponse sendMailCertification(@RequestBody String email) throws JsonProcessingException {
        return memberService.sendCertificationMail(email);
    }
}
