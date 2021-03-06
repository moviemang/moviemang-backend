package com.moviemang.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.exception.BaseException;
import com.moviemang.coreutils.common.exception.InvalidParamException;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.datastore.dto.mail.MailCertificationDto;
import com.moviemang.datastore.dto.member.MemberJoinDto;
import com.moviemang.datastore.entity.maria.MailCertification;
import com.moviemang.datastore.entity.maria.MailServiceUser;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.repository.maria.DeletedMemberRepository;
import com.moviemang.datastore.repository.maria.MailCertificationRepository;
import com.moviemang.datastore.repository.maria.MailUserRepository;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.member.dto.DeletedMember;
import com.moviemang.member.dto.MemberInfo;
import com.moviemang.member.dto.MyPage;
import com.moviemang.member.dto.MyPageInfo;
import com.moviemang.member.encrypt.CommonEncoder;
import com.moviemang.member.mapper.MemberMapper;
import com.moviemang.member.util.CreateCertificationUtil;
import com.moviemang.member.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
	private MailUserRepository mailUserRepository;
	private ObjectMapper om;
	private MemberMapper memberMapper;

	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository, CommonEncoder commonEncoder, DeletedMemberRepository deletedMemberRepository, MailCertificationRepository mailRepo
			, MailUtil mailUtil, MailUserServiceImpl mailUserServiceImpl, ObjectMapper om, MemberMapper memberMapper, MailUserRepository mailUserRepository) {
		this.memberRepository = memberRepository;
		this.commonEncoder = commonEncoder;
		this.deletedMemberRepository = deletedMemberRepository;
		this.mailRepo = mailRepo;
		this.mailUtil = mailUtil;
		this.mailUserServiceImpl = mailUserServiceImpl;
		this.om = om;
		this.memberMapper = memberMapper;
		this.mailUserRepository = mailUserRepository;
	}

	/**
	 * ???????????? ?????????
	 * @author hwang.kh
	 * @param memberJoinDto
	 * @return {@link ResponseEntity}
	 */
    @Override
    public ResponseEntity regist(MemberJoinDto memberJoinDto) {

        // ???????????? ?????????
		String encodePassword = commonEncoder.encode(memberJoinDto.getMemberPassword());
		// ?????? ???????????? ?????? ?????? ??????
		Member joinUser =Member.builder()
				.memberEmail(memberJoinDto.getMemberEmail())
				.memberName(memberJoinDto.getMemberName())
				.memberPassword(encodePassword)
				.build();
		try {
			Member resultMember = memberRepository.save(joinUser);
			log.info("resultMember  : "+resultMember.toString());

			// if else??? ??????????????? ????????? Exception ??????????????? ????????? ?????? ?????????? ?????? ??????
			if(resultMember==null){
				return ResponseEntity.status(500).body(CommonResponse.fail(ErrorCode.COMMON_ILLEGAL_STATUS));
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

		return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.builder()
				.result(CommonResponse.Result.SUCCESS)
				.message("??????????????? ?????????????????????.")
				.build());
    }
	/**
	 * ????????? ???????????? ?????????
	 * @author hwang.kh
	 * @param email
	 * @return {@link ResponseEntity}
	 */
    @Override
    public ResponseEntity checkEmail(String email) {
        int duplicatedUser = memberRepository.countMemberByMemberEmail(email);

		if(duplicatedUser!=0) return ResponseEntity.ok(CommonResponse.fail(ErrorCode.EMAIL_DUPLICATED));
		else return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(CommonResponse.Result.SUCCESS,"?????? ????????? ??????????????????."));
    }

	/**
	 * ????????? ???????????? ?????????
	 * @author hwang.kh
	 * @param nick
	 * @return {@link ResponseEntity}
	 */
	@Override
	public ResponseEntity checkNick(String nick) {
		int duplicatedUser = memberRepository.countMemberByMemberName(nick);

		if(duplicatedUser!=0) return ResponseEntity.ok(CommonResponse.fail(ErrorCode.NICK_DUPLICATED));
		else return  ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(CommonResponse.Result.SUCCESS,"?????? ????????? ??????????????????."));
	}

	/**
	 * ?????? ??????
	 * @param deletedMember
	 * @return
	 */
	@Override
	public ResponseEntity deleteMember(DeletedMember deletedMember) {
		try{
			memberRepository.deleteById(deletedMember.getId());

			deletedMemberRepository.save(com.moviemang.datastore.entity.maria.DeletedMember.builder()
							.memberEmail(deletedMember.getEmail())
					.build());

			return ResponseEntity.ok(CommonResponse.builder()
					.result(CommonResponse.Result.SUCCESS)
					.message("?????? ????????? ?????????????????????.")
					.build());

		}catch (EmptyResultDataAccessException e){
			return ResponseEntity.status(500).body(CommonResponse.success(null,ErrorCode.COMMON_ENTITY_NOT_FOUND.getErrorMsg()));

		}catch (Exception e){
			throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);

		}
	}

	public ResponseEntity sendCertificationMail(String memberEmail) throws JsonProcessingException {
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
			return ResponseEntity.status(500).body(CommonResponse.fail(ErrorCode.MAIL_SYSTEM_ERROR));
		}
		return  ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(null, "?????? ?????? ??????"));
	}

	@Override
	public List<MemberInfo> selectAllMembers() {
		List<Member> members = memberRepository.findAll();
		return memberMapper.of(members);
	}

	@SuppressWarnings("static-access")
	public ResponseEntity checkMailCertification(MailCertificationDto certificationDto) {

		MailCertification certificationInfo = mailRepo.findTop1ByMemberEmailOrderByRegDateDesc(certificationDto.getMemberEmail());
		LocalDateTime mailSendTime = certificationInfo.getRegDate();
		LocalDateTime minus5Time = certificationDto.getClickedTime().minusMinutes(5);

		if(minus5Time.isAfter(mailSendTime)) {
			return ResponseEntity.ok(CommonResponse.fail(ErrorCode.CERTIFICATION_TIMED_OUT));
		}
		else {
			if(StringUtils.equals(certificationInfo.getMailCertificationMsg(), certificationDto.getMailCertificationMsg())) {
				return ResponseEntity.ok(CommonResponse.success(null, "?????? ??????"));
			}
			else {
				return ResponseEntity.ok(CommonResponse.fail(ErrorCode.CERTIFICATION_NOT_EQUAL));
			}
		}
	}

	@Override
	public ResponseEntity myInfo(MyPage.Request request) {
		MyPageInfo.MyPageInfoBuilder myPageInfo = MyPageInfo.builder();

		try{
			if(!mailUserRepository.findById(request.getId()).isPresent()){
				myPageInfo.mailServiceUseYn("N");
			}
			else{
				myPageInfo.mailServiceUseYn("Y");
			}

			Member member = memberRepository.findByMemberId(request.getId()).orElse(null);
			if(member == null){
				throw new BaseException(ErrorCode.USER_NOT_FOUND);
			}
			else{
				myPageInfo.id(request.getId());
				myPageInfo.nickname(member.getMemberName());
			}
		} catch (Exception e){
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
		}

		return ResponseEntity.ok(CommonResponse.success(myPageInfo.build()));
	}

	@Override
	public ResponseEntity changeName(MyPage.Request request, String nickname) throws JsonProcessingException {
		Map<String, String> bodyMap = om.readValue(nickname, Map.class);
		String extractName = bodyMap.get("nickname");

		if(StringUtils.isEmpty(extractName)){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER));
		}

		int duplicatedUser = memberRepository.countMemberByMemberName(extractName);
		if(duplicatedUser != 0){
			return ResponseEntity.ok(CommonResponse.fail(ErrorCode.NICK_DUPLICATED));
		}

		Member member = memberRepository.findByMemberId(request.getId()).orElse(null);
		if(member == null){
			throw new BaseException(ErrorCode.USER_NOT_FOUND);
		}
		member.setMemberName(extractName);

		try{
			memberRepository.save(member);
		} catch (BaseException e){
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(null, "????????? ?????? ??????"));
	}

	@Override
	public ResponseEntity changeMailService(MyPage.Request request, String mailServiceUseYn) throws JsonProcessingException {
		Map<String, String> bodyMap = om.readValue(mailServiceUseYn, Map.class);
		String extractUseYn = bodyMap.get("mailServiceUseYn");

		try{
			MailServiceUser mailServiceUser = mailUserRepository.findByMemberId(request.getId()).orElse(null);
			if(StringUtils.equals(extractUseYn, "Y")){
					mailUserRepository.save(MailServiceUser.builder()
							.memberId(request.getId())
							.memberEmail(request.getEmail())
							.contentType("M")	//????????? ?????? ???????????? ????????? M
							.build());
			} else {
				assert mailServiceUser != null;
				mailUserRepository.delete(mailServiceUser);
			}

		} catch (BaseException e){
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(null, "?????? ?????? ????????? ?????? ?????? ??????"));
	}

	@Override
	public ResponseEntity changePassword(MyPage.Request request, String password) throws JsonProcessingException {
		Map<String, String> bodyMap = om.readValue(password, Map.class);
		String extractPassword = bodyMap.get("password");

		if(StringUtils.isEmpty(extractPassword)){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER));
		}

		Member member = memberRepository.findByMemberId(request.getId()).orElse(null);
		if(member == null){
			throw new BaseException(ErrorCode.USER_NOT_FOUND);
		}

		if(commonEncoder.matches(extractPassword, member.getMemberPassword())){
			return ResponseEntity.ok(CommonResponse.fail(ErrorCode.PASSWORD_IS_EQUASL));
		}
		try{
			member.setMemberPassword(commonEncoder.encode(extractPassword));
			memberRepository.save(member);
		} catch (Exception e){
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(null, "???????????? ?????? ??????"));
	}
}
