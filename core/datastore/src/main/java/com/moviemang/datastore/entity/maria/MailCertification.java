package com.moviemang.datastore.entity.maria;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MailCertification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mailCertificationId;
	
	@Column(nullable = false, length = 30)
	private String memberEmail;
	
	@Column(nullable = false, length = 10)
	private String mailCertificationMsg;
	
	@CreatedDate
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
