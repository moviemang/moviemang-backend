package com.moviemang.coreutils.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),

    // Join CHECK Exception
    NICK_NOT_FOUND("이미 사용중인 닉네임입니다"),

    // Auth Exception
    USER_NOT_FOUND("회원을 찾을수 없습니다."),

    AUTH_INVALID_SIGNATURE_JWT("토큰이 유효하지 않습니다."),
    AUTH_MALFORMED_JWT("토큰이 유효하지 않습니다."),
    AUTH_ILLEGAL_ARGUMENT_JWT("토큰이 유효하지 않습니다."),
    AUTH_UNSUPPORTED_JWT("토큰이 유효하지 않습니다."),
    AUTH_INVALID_JWT("유효하지 않은 토큰입니다."),
    AUTH_EXPIRED_JWT("토큰 유효시간이 지났습니다."),
    AUTH_REFRESH_TOKEN_INVALID("리프레시 토큰이 유효하지 않습니다."),
    AUTH_LOGIN_FAIL("아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요."),

	// Mail Exception
    MAIL_NOT_FOUND("이미 사용중인 이메일입니다"),
    MAIL_SYSTEM_ERROR("메일 발송 중 오류 발생하였습니다. 다시 시도해주세요."),
    CERTIFICATION_TIMED_OUT("메일 인증시간이 초과하였습니다. 다시 인증해주세요."),
    CERTIFICATION_NOT_EQUAL("인증 번호가 일치하지 않습니다.");


    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
	
}
