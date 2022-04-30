package com.moviemang.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.exception.InvalidParamException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.datastore.domain.MailCertificationDto;
import com.moviemang.datastore.entity.maria.MailCertification;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.repository.maria.MailCertificationRepository;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.member.encrypt.CommonEncoder;
import com.moviemang.member.util.CreateCertificationUtil;
import com.moviemang.member.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;
    private CommonEncoder commonEncoder;
	private MailCertificationRepository mailRepo;
	private MailUtil mailUtil;
	private ObjectMapper om;

	@Autowired
	public MemberServiceImpl(MailUtil mailUtil, MailCertificationRepository mailRepo,MemberRepository memberRepository) {
		this.mailRepo = mailRepo;
		this.mailUtil = mailUtil;
		this.memberRepository = memberRepository;
		this.commonEncoder = new CommonEncoder();
		this.om = new ObjectMapper();
	}

    @Override
    public Member regist(Member member) {
        log.info("");
        // 비밀번호 암호화
        member.setMemberPassword(commonEncoder.encode(member.getMemberPassword()));

        // regist 처리
        memberRepository.save(member);
        return null;
    }

    @Override
    public CommonResponse checkEmail(String email) {
        int duplicatedUser = memberRepository.countMemberByMemberEmail(email);

		if(!(duplicatedUser>0)) return CommonResponse.success(CommonResponse.Result.FAIL);
		else return CommonResponse.success(CommonResponse.Result.SUCCESS);

    }

	@Override
	public CommonResponse checkNick(String nick) {
		int duplicatedUser = memberRepository.countMemberByMemberName(nick);

		if(!(duplicatedUser>0)) return CommonResponse.success(CommonResponse.Result.FAIL);
		else return CommonResponse.success(CommonResponse.Result.SUCCESS);
	}

	public CommonResponse sendCertificationMail(String memberEmail) throws JsonProcessingException {
		String certificationStr = CreateCertificationUtil.create(false, 6);
		if(StringUtils.isEmpty(memberEmail)){
			throw new InvalidParamException();
		}
		boolean isSuccess = false;
		Map<String, String> map = om.readValue(memberEmail, Map.class);
		try {
			MailCertification certificationInfo = MailCertification.builder()
					.memberEmail(map.get("member_email"))
					.mailCertificationMsg(certificationStr)
					.regDate(LocalDateTime.now())
					.build();
			mailRepo.save(certificationInfo);
		} catch (Exception e) {
			log.error("email param setting error : {}", e.getMessage());
			throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
		}
		isSuccess = mailUtil.certificationMailSend(certificationStr, map.get("member_email"));
		if(!isSuccess) {
			return CommonResponse.fail(ErrorCode.MAIL_SYSTEM_ERROR);
		}
		return CommonResponse.success(null, "메일 발송 성공", HttpStatus.CREATED);
	}

	@SuppressWarnings("static-access")
	public CommonResponse checkMailCertification(MailCertificationDto certificationDto) {

		MailCertification certificationInfo = mailRepo.findTop1ByMemberEmailOrderByRegDateDesc(certificationDto.getMemberEmail());
		LocalDateTime mailSendTime = certificationInfo.getRegDate();
		LocalDateTime minus5Time = certificationDto.getClickedTime().minusMinutes(5);

		if(minus5Time.isAfter(mailSendTime)) {
			return CommonResponse.fail(ErrorCode.CERTIFICATION_TIMED_OUT);
		}
		else {
			if(StringUtils.equals(certificationInfo.getMailCertificationMsg(), certificationDto.getMailCertificationMsg())) {
				return CommonResponse.success(null, "인증 성공", HttpStatus.CREATED);
			}
			else {
				return CommonResponse.fail(ErrorCode.CERTIFICATION_NOT_EQUAL);
			}
		}
	}
}
