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

@CrossOrigin("*")
@RequestMapping("/member")
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

        boolean isDuplicated =memberService.checkEmail(email);
        if(isDuplicated){
            return CommonResponse.builder()
                .result(CommonResponse.Result.FAIL)
                .build();
        }else{
            return CommonResponse.builder()
                .result(CommonResponse.Result.SUCCESS)
                .build();
        }

    }
}
