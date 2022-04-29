package com.moviemang.datastore.entity.maria;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Data
@Table(name = "mail_service_user")
public class MailServiceUser extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_service_id")
    private Long mailServiceId;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "mebmer_email")
    private String memberEmail;
    @Column(name = "content_type")
    private String contentType;

    @Builder

    public MailServiceUser(Long mailServiceId,Long memberId, String memberEmail, String contentType) {
        this.mailServiceId = mailServiceId;
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.contentType = contentType;
    }
}
