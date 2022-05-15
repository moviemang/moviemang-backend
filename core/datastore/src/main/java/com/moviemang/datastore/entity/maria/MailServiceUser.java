package com.moviemang.datastore.entity.maria;

import lombok.*;

import javax.persistence.*;

@Entity(name = "mail_service_user")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailServiceUser extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_service_id")
    private Long mailServiceId;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "member_email")
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
