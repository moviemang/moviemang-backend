package com.moviemang.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
public class MemberInfo {
    private Long id;

    private String email; // 이메일

    private String name; // 닉네임

    private String password; // 비밀번호

}
