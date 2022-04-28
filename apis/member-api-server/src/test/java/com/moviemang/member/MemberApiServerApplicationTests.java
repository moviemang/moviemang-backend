package com.moviemang.member;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableJpaAuditing
@SpringBootTest
class MemberApiServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MemberService memberService;

    @Test
    void registMember() {
        MemberJoinDto member = MemberJoinDto.builder()
                .memberEmail("testuser45@gmail.com")
                .memberName("쿠1쿠2쿠3")
                .memberPassword("testpassword1354")
                .build();

        memberService.regist(member);
        log.info("member :  "+member.toString());
    }
    @Test
    void checkEmail() {
        String email = "testuser5@gmail.com";
        CommonResponse isDuplicated =memberService.checkEmail(email);

        log.info("member :  "+isDuplicated);
    }

}
