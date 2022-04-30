package com.moviemang.security.service;

import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.security.domain.CustomMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private MemberRepository memberRepository;

    @Autowired
    public UserDetailServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member user = memberRepository.findByEmail(username)
//                .orElseThrow(()->new UsernameNotFoundException("Member: "+ username+ " not found"));
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
//    }

    @Override
    public CustomMember loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findByMemberEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("Member: "+ username+ " not found"));
        return buildUserForAuthentication(user, username,  Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private CustomMember buildUserForAuthentication(Member member, String username, List<GrantedAuthority> authorities) {
        if(member != null) {
            CustomMember customUser = CustomMember.builder()
                    .id(member.getMemberId())
                    .username(username)
                    .authorities(authorities)
                    .password(member.getMemberPassword())
                    .accountNonExpired(true)
                    .enabled(true)
                    .credentialsNonExpired(true)
                    .accountNonLocked(true)
                    .build();

            return customUser;
        }
        return null;
    }
}
