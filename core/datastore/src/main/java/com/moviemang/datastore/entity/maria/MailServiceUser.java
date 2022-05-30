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
    @Column(name = "member_id", nullable = false)
    private Long memberId;
    @Column(name = "member_email", length = 30, nullable = false)
    private String memberEmail;
    @Column(name = "content_type", length = 1, nullable = false)
    private String contentType;

    @Builder
    public MailServiceUser(Long mailServiceId,Long memberId, String memberEmail, String contentType) {
        this.mailServiceId = mailServiceId;
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.contentType = contentType;
    }
}
