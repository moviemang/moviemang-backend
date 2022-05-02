package com.moviemang.datastore.entity.maria;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("member_id")
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

    @Column(name = "member_type")
    private String memberType;

    @Builder
    public Member(Long memberId, String memberEmail, String memberName, String memberPassword, String memberType) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberType =  "N";
    }

}
