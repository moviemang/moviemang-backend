package com.moviemang.member.util;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MailUtil {
	
	@Autowired
	private JavaMailSender mailSender;

	@SuppressWarnings("static-access")
	public boolean certificationMailSend(String certificationMsg, String targetEmail) {
		MimeMessage mail = mailSender.createMimeMessage();
		String htmlStr = "<h2>회원가입 인증 번호입니다.</h2><br><br>";
        
        try {
        	mail.setSubject("[회원인증] 회원가입 인증메일입니다", "utf-8");
    		htmlStr = "<h3>" + certificationMsg + "</h3>";
            mail.setText(htmlStr, "utf-8", "html");
            mail.addRecipient(RecipientType.TO, new InternetAddress(targetEmail));
            mailSender.send(mail);
		} catch (MailException | MessagingException e) {
			log.error("메일 발송 오류 : {}", e.getMessage());
			return false;
		}
		return true;
	}

	@SuppressWarnings("static-access")
	public boolean passwordRestMailSend(String certificationMsg, String targetEmail) {
		MimeMessage mail = mailSender.createMimeMessage();
		try {
			String htmlStr = "<h2>임시 비밀번호입니다.</h2><br><br>";
			mail.setSubject("[비밀번호 초기화] 임시 비밀번호입니다.", "utf-8");
			
			htmlStr = "<h3>" + certificationMsg + "</h3>";
	        mail.setText(htmlStr, "utf-8", "html");
	        mail.addRecipient(RecipientType.TO, new InternetAddress(targetEmail));
	        mailSender.send(mail);
		} catch (MailException | MessagingException e) {
			log.error("메일 발송 오류 : {}", e.getMessage());
			return false;
		}
		return true;
	}
}
