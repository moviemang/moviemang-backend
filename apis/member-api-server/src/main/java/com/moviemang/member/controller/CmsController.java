package com.moviemang.member.controller;

import com.moviemang.member.dto.MemberInfo;
import com.moviemang.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(path = "/cms")
@RestController
public class CmsController {
    private MemberService memberService;

    @Autowired
    public CmsController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public List<MemberInfo> selectAllMemberInfo(){
        return memberService.selectAllMembers();
    }
}
