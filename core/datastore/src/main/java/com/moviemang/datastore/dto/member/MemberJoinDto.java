package com.moviemang.datastore.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviemang.datastore.entity.maria.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJoinDto extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Email(message = "이메일을 올바르게 입력하세요.")
    @JsonProperty("email")
    @Column(name = "member_email")
    private String memberEmail; // 이메일

    @NotBlank
    @JsonProperty("name")
    @Column(name = "member_name")
    private String memberName; // 닉네임

    @NotBlank
    @JsonProperty("password")
    @Column(name = "member_password")
    private String memberPassword; // 비밀번호

    @NotBlank
    @JsonProperty("mail_service_use_yn")
    private String mailServiceUseYn; // 메일 구독 여부

    @Builder
    public MemberJoinDto(Long memberId, String memberEmail, String memberName,
                         String memberPassword,String mailServiceUseYn) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.mailServiceUseYn = mailServiceUseYn;
    }

}
