package com.moviemang.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
//@EnableJpaAuditing
@Profile("local")
//@AutoConfigureMockMvc
@SpringBootTest
class MemberApiServerApplicationTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Autowired
    private MemberService memberService;


    @Test
    void registMember() {

        MemberJoinDto memberJoinDto = MemberJoinDto.builder()
                .memberEmail("testusera45@gmail.com")
                .memberName("???1???2???3aa")
                .memberPassword("testusera45")
                .mailServiceUseYn("??????")
                .build();

        memberService.regist(memberJoinDto);
        Member joinUser =Member.builder().build();
        BeanUtils.copyProperties(memberJoinDto,joinUser,"memberId","mailServiceUseYn");

//        memberService.regist(member);
//        log.info("memberJoinDto   :  "+memberJoinDto.toString());
//        log.info("joinUser   :  "+joinUser.toString());

    }
    @Test
    void checkEmail() {
        String email = "testuser5@gmail.com";
        CommonResponse commonResponse =memberService.checkEmail(email);
    }

    @Test
    void insertUser(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        MemberJoinDto memberJoinDto = MemberJoinDto.builder()
                .memberEmail("test5@gmail.com")
                .memberName("???1???2???3aa")
                .memberPassword("pass5")
                .mailServiceUseYn("Y")
                .build();
        memberService.regist(memberJoinDto);
    }

//    @Test
//    @WithMockUser(username = "test1@naver.com", password = "test1")
//    void  login(){
//        Map<String, String> input = new HashMap<>();
//        // body??? json ???????????? ????????? ???????????? ?????? ????????? Map??? ????????????.
//        input.put("username", "test2@naver.com");
//        input.put("password", "pass2");
//        try {
//            mockMvc.perform(post("/login")
//                            .contentType("application/json")
//                            .content(new ObjectMapper().writeValueAsString(input))
//                    )
//
//
//                    .andExpect(status().isOk())
//                    //Http 200??? ??????
//                    .andDo(print());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
