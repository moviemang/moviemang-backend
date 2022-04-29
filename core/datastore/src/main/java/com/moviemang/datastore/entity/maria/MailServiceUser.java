package com.moviemang.datastore.entity.maria;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "mail_service_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailServiceUser {
    @Id
    @Column(name = "mail_service_id")
    private Long mailServiceId;
    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "content_type")
    private String contentType;

    @Builder
    public MailServiceUser(Long mailServiceId,Member member,
           String memberEmail,String contentType){
        this.mailServiceId=mailServiceId;
        this.member=member;
        this.memberEmail=memberEmail;
        this.contentType=contentType;
    }
}
