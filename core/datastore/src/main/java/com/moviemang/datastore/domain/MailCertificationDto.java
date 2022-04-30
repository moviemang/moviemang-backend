package com.moviemang.datastore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MailCertificationDto {

	@Column(name = "member_email")
	@JsonProperty("member_email")
	private String memberEmail;

	@Column(name = "mail_certification_msg")
	@JsonProperty("mail_certification_msg")
	private String mailCertificationMsg;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "clicked_time")
	@JsonProperty("clicked_time")
	private LocalDateTime clickedTime;

	@Builder
	public MailCertificationDto(String memberEmail, String mailCertificationMsg, LocalDateTime clickedTime) {
		this.memberEmail = memberEmail;
		this.mailCertificationMsg = mailCertificationMsg;
		this.clickedTime = clickedTime;
	}
	
}
