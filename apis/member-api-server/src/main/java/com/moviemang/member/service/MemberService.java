package com.moviemang.member.service;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.datastore.domain.MailCertificationDto;

@SuppressWarnings("rawtypes")
public interface MemberService {
	
	CommonResponse checkMailCertification(MailCertificationDto certificationDto);
}
