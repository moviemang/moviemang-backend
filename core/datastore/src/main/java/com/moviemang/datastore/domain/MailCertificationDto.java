package com.moviemang.datastore.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MailCertificationDto {

	@NotNull
	@JsonProperty("member_email")
	private String memberEmail;
	
	@NotNull
	@JsonProperty("mail_certification_msg")
	private String mailCertificationMsg;
	
	@NotNull
	@JsonProperty("clicked_time")
	private String clickedTime;

	@Builder
	public MailCertificationDto(String memberEmail, String mailCertificationMsg, String clickedTime) {
		this.memberEmail = memberEmail;
		this.mailCertificationMsg = mailCertificationMsg;
		this.clickedTime = clickedTime;
	}
	
}
