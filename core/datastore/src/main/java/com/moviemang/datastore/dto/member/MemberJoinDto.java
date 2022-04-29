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
    @JsonProperty("member_email")
    @Column(name = "member_email")
    private String memberEmail; // 이메일

    @NotBlank
    @JsonProperty("member_name")
    @Column(name = "member_name")
    private String memberName; // 닉네임

    @NotBlank
    @JsonProperty("member_password")
    @Column(name = "member_password")
    private String memberPassword; // 비밀번호

    private String mail_service_useYn; //구독종류
    @Builder
    public MemberJoinDto(Long memberId, String memberEmail, String memberName,
                         String memberPassword,String mail_service_useYn) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.mail_service_useYn = mail_service_useYn;
    }

}
