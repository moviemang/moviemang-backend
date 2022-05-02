package com.moviemang.datastore.entity.maria;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mail_certification")
public class MailCertification{

	@Id
	@Column(name = "mail_certification_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mailCertificationId;

	@Column(name = "member_email", nullable = false, length = 30)
	private String memberEmail;
	
	@Column(name = "mail_certification_msg", nullable = false, length = 10)
	private String mailCertificationMsg;

	@CreatedDate
	@Column(name = "reg_date")
	private LocalDateTime regDate;

	@Builder
	public MailCertification(Long mailCertificationId, String memberEmail, String mailCertificationMsg,
			LocalDateTime regDate, String mailCertificationSuccess) {
		this.mailCertificationId = mailCertificationId;
		this.memberEmail = memberEmail;
		this.mailCertificationMsg = mailCertificationMsg;
		this.regDate = regDate;
	}
}
