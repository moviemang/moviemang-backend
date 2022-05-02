package com.moviemang.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.exception.InvalidParamException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.datastore.dto.MailCertificationDto;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.datastore.entity.maria.MailCertification;
import com.moviemang.datastore.entity.maria.MailServiceUser;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.repository.maria.DeletedMemberRepository;
import com.moviemang.datastore.repository.maria.MailCertificationRepository;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.member.dto.DeletedMember;
import com.moviemang.member.encrypt.CommonEncoder;
import com.moviemang.member.util.CreateCertificationUtil;
import com.moviemang.member.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@Transactional(rollbackFor=Exception.class)
public class MemberServiceImpl implements MemberService{

	private MemberRepository memberRepository;
    private CommonEncoder commonEncoder;
	private DeletedMemberRepository deletedMemberRepository;
	private MailCertificationRepository mailRepo;
	private MailUtil mailUtil;
	private MailUserServiceImpl mailUserServiceImpl;
	private ObjectMapper om;

	@Autowired
	public MemberServiceImpl(MailUtil mailUtil, MailCertificationRepository mailRepo,
	 MemberRepository memberRepository, DeletedMemberRepository deletedMemberRepository, MailUserServiceImpl mailUserServiceImpl) {
		this.mailRepo = mailRepo;
		this.mailUtil = mailUtil;
		this.memberRepository = memberRepository;
		this.deletedMemberRepository = deletedMemberRepository;
		this.commonEncoder = new CommonEncoder();
		this.om = new ObjectMapper();
		this.mailUserServiceImpl = mailUserServiceImpl;
	}

	/**
	 * 회원가입 메소드
	 * @author hwang.kh
	 * @param memberJoinDto
	 * @return {@link CommonResponse}
	 */
    @Override
    public CommonResponse regist(MemberJoinDto memberJoinDto) {

        // 비밀번호 암호화
		String encodePassword = commonEncoder.encode(memberJoinDto.getMemberPassword());
		// 멤버 테이블에 넣을 정보 세팅
		Member joinUser =Member.builder()
				.memberEmail(memberJoinDto.getMemberEmail())
				.memberName(memberJoinDto.getMemberName())
				.memberPassword(encodePassword)
				.build();
		try {
			Member resultMember = memberRepository.save(joinUser);
			log.info("resultMember  : "+resultMember.toString());

			// if else로 돌려주는게 아니라 Exception 클래스에서 하는게 맞지 않을까? 논의 필요
			if(resultMember==null){
				return CommonResponse.fail(ErrorCode.COMMON_ILLEGAL_STATUS);
			}
			if(!memberJoinDto.getMailServiceUseYn().equalsIgnoreCase("")){
				MailServiceUser mailServiceUser = MailServiceUser.builder()
						.memberId(resultMember.getMemberId())
						.memberEmail(resultMember.getMemberEmail())
						.contentType(memberJoinDto.getMailServiceUseYn())
						.build();
				log.info("mailServiceUser  : "+mailServiceUser.toString());
				MailServiceUser result = mailUserServiceImpl.memberJoin(mailServiceUser);

				log.info("Result MailServiceUser  : "+result.toString());
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
		}

		return CommonResponse.builder()
				.result(CommonResponse.Result.SUCCESS)
				.status(HttpStatus.CREATED)
				.build();
    }
	/**
	 * 이메일 중복체크 메소드
	 * @author hwang.kh
	 * @param email
	 * @return {@link CommonResponse}
	 */
    @Override
    public CommonResponse checkEmail(String email) {
        int duplicatedUser = memberRepository.countMemberByMemberEmail(email);

		if(duplicatedUser!=0) return CommonResponse.fail(ErrorCode.MAIL_NOT_FOUND);
		else return CommonResponse.success(CommonResponse.Result.SUCCESS);
    }

	/**
	 * 닉네임 중복체크 메소드
	 * @author hwang.kh
	 * @param nick
	 * @return {@link CommonResponse}
	 */
	@Override
	public CommonResponse checkNick(String nick) {
		int duplicatedUser = memberRepository.countMemberByMemberName(nick);

		if(duplicatedUser!=0) return CommonResponse.fail(ErrorCode.NICK_NOT_FOUND);
		else return CommonResponse.success(CommonResponse.Result.SUCCESS);
	}

	/**
	 * 회원 탈퇴
	 * @param deletedMember
	 * @return
	 */
	@Override
	public CommonResponse deleteMember(DeletedMember deletedMember) {
		try{
//			System.out.println("[Service] delete member id :" + deletedMember);

			// member 테이블에서 회원 삭제
			memberRepository.deleteById(deletedMember.getId());

			// deleted_member 테이블에 탈퇴 회원 추가
			deletedMemberRepository.save(com.moviemang.datastore.entity.maria.DeletedMember.builder()
							.memberEmail(deletedMember.getEmail())
					.build());

			return CommonResponse.builder()
					.result(CommonResponse.Result.SUCCESS)
					.message("회원 탈퇴가 완료되었습니다.")
					.build();

		}catch (EmptyResultDataAccessException e){
			return CommonResponse.success(null,ErrorCode.COMMON_ENTITY_NOT_FOUND.getErrorMsg(), HttpStatus.NO_CONTENT);

		}catch (Exception e){
			throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);

		}
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
