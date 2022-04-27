package com.moviemang.member;

import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableJpaAuditing
@Profile("local")
@SpringBootTest
class MemberApiServerApplicationTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void contextLoads() {
    }

    @Autowired
    private MemberService memberService;

    @Test
    void registMember() {
        Member member = Member.builder()
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
        boolean isDuplicated =memberService.checkEmail(email);

        log.info("member :  "+isDuplicated);
    }

    @Test
    void insertUser(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        memberRepository.save(Member.builder()
                .email("test2@naver.com")
                .password(bCryptPasswordEncoder.encode("pass2"))
                .build());
    }

}
