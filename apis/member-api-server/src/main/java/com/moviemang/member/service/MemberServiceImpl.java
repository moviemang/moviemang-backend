package com.moviemang.member.service;

import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.member.encrypt.CommonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;


    private CommonEncoder commonEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository,CommonEncoder commonEncoder){
        this.memberRepository = memberRepository;
        this.commonEncoder = commonEncoder;
    }

    @Override
    public Member regist(Member member) {
        log.info("");
        // 비밀번호 암호화
        member.setMemberPassword(commonEncoder.encode(member.getMemberPassword()));

        // regist 처리
        memberRepository.save(member);
        return null;
    }

    @Override
    public boolean checkEmail(String email) {
        Optional<Member> duplicatedUser =memberRepository.findMemberByMemberEmail(email);

        return duplicatedUser.isPresent()?true:false;

    }
}
