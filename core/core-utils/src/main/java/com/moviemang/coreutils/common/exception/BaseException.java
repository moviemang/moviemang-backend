package com.moviemang.coreutils.common.exception;

import com.moviemang.coreutils.common.response.ErrorCode;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class BaseException extends RuntimeException {
	private ErrorCode errorCode;

    public BaseException() {
    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());	// 부모 클래스인 RuntimeException의 생성자를 호출
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
