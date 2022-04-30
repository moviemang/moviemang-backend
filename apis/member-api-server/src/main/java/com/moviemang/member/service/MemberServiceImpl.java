package com.moviemang.member.service;

import com.moviemang.datastore.entity.maria.DeletedMember;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.repository.maria.DeletedMemberRepository;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.member.encrypt.CommonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.datastore.domain.MailCertificationDto;
import com.moviemang.datastore.entity.maria.MailCertification;
import com.moviemang.datastore.repository.maria.MailCertificationRepository;
import com.moviemang.member.util.CreateCertificationUtil;
import com.moviemang.member.util.MailUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

	private MemberRepository memberRepository;
    private CommonEncoder commonEncoder;
	private DeletedMemberRepository deletedMemberRepository;
	private MailCertificationRepository mailRepo;
	private MailUtil mailUtil;
	@Autowired
	public MemberServiceImpl(MailUtil mailUtil, MailCertificationRepository mailRepo,
							 MemberRepository memberRepository, DeletedMemberRepository deletedMemberRepository) {
		this.mailRepo = mailRepo;
		this.mailUtil = mailUtil;
		this.memberRepository = memberRepository;
		this.deletedMemberRepository = deletedMemberRepository;
		this.commonEncoder = new CommonEncoder();

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

	/**
	 * 회원 탈퇴
	 * @param memberId
	 */
	@Override
	public CommonResponse deleteMember(Long memberId) {
		try{
			System.out.println("[Service] delete member id :" + memberId);
			// member 테이블에서 회원 삭제
			memberRepository.deleteById(memberId);

			// deleted_member 테이블에 탈퇴 회원 추가
//			deletedMemberRepository.save();

			return CommonResponse.builder()
					.result(CommonResponse.Result.SUCCESS)
					.message("회원 탈퇴가 완료되었습니다.")
					.build();

		}catch (EmptyResultDataAccessException e){
			throw new BaseException(ErrorCode.COMMON_ENTITY_NOT_FOUND);

		}catch (Exception e){
			throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);

		}

		/*
		// 실패시 - TODO: HTTP STATUS 변경 필요 (nO_CONTENT 204)
		{
			"result": "FAIL",
			"data": null,
			"message": "존재하지 않는 엔티티입니다.",
			"error_code": "COMMON_ENTITY_NOT_FOUND"
		}
		// 성공시
		{
			"result": "SUCCESS",
			"data": "SUCCESS",
			"message": null,
			"error_code": null
		}
		 */
	}

	/**
	 * 이메일 중복체크 클릭 시 인증메일 발송하는 메소드
	 * @author kang.dj
	 * @param memberEmail
	 * @return {@link Boolean}
	 */
	public boolean sendCertificationMail(String memberEmail) {
		String certificationStr = CreateCertificationUtil.create(false, 6);	// 인증번호 생성
		LocalDateTime now = LocalDateTime.now();	// 메일 전송 시간

		try {
			// 이메일 인증정보 세팅
			MailCertification certificationInfo = MailCertification.builder()
					.memberEmail(memberEmail)
					.mailCertificationMsg(certificationStr)
					.regDate(now)
					.build();

			// 회원 이메일 및 인증번호와 인증 메일 전송 시간 저장
			mailRepo.save(certificationInfo);
		} catch (Exception e) {
			log.error("이메일 인증 데이터 세팅 중 오류 발생 : {}", e.getMessage());
			throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
		}

		boolean isSuccess = mailUtil.certificationMailSend(certificationStr, memberEmail);

		if(!isSuccess) {
			return false;
		}
		return true;
	}

	/*
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
