package com.moviemang.member.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.datastore.domain.MailCertificationDto;
import com.moviemang.datastore.entity.maria.MailCertification;
import com.moviemang.datastore.repository.maria.MailCertificationRepository;
import com.moviemang.member.util.MailUtil;

@Service
public class MemberServiceImpl implements MemberService{
	
	private MailCertificationRepository mailRepo;
	
	private MailUtil mailUtil;
	
	@Autowired
	public MemberServiceImpl(MailUtil mailUtil, MailCertificationRepository mailRepo) {
		this.mailRepo = mailRepo;
		this.mailUtil = mailUtil;
	}
	
	/**
	 * 이메일 인증 버튼 클릭 시 인증번호 및 유효시간 체크
	 * @param memberEmail
	 * @param mailCertificationMsg
	 * @param clickTime
	 * @return {@link CommonResponse}
	 */
	@SuppressWarnings("static-access")
	public CommonResponse checkMailCertification(MailCertificationDto certificationDto) {
		// String => LocalDate 포맷터
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		// 해당 이메일로 등록된 데이터 중 가장 최신 row 가져옴
		MailCertification certificationInfo = mailRepo.findTop1ByMemberEmailOrderByRegDateDesc(certificationDto.getMemberEmail());
		
		// 해당하는 이메일이 없을 경우
		
		
		LocalDateTime mailSendTime = certificationInfo.getRegDate(); // 메일 보낸 시간
		LocalDateTime formattedDate = LocalDateTime.parse(certificationDto.getClickedTime(), formatter); // 클릭한 시간 포맷팅 String => LocalDateTime
		LocalDateTime minus5Time = formattedDate.minusMinutes(5); // 클릭한 시간 - 5분
		
		if(minus5Time.isAfter(mailSendTime)) {
			// 인증시간 초과
			return CommonResponse.fail(ErrorCode.CERTIFICATION_TIMED_OUT);
		}
		else {
			if(StringUtils.equals(certificationInfo.getMailCertificationMsg(), certificationDto.getMailCertificationMsg())) {
				// 인증번호 일치
				return CommonResponse.success(null, "인증 성공", HttpStatus.CREATED);
			}
			else {
				// 인증번호 불일치
				return CommonResponse.fail(ErrorCode.CERTIFICATION_NOT_EQUAL);
			}
		}
	}
}
