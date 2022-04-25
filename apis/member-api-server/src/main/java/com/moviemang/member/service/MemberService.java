package com.moviemang.member.service;

import com.moviemang.datastore.entity.maria.Member;

public interface MemberService {
    public Member regist(Member member);
    public boolean checkEmail(String email);
}
