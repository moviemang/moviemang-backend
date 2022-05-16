package com.moviemang.coreutils.common.exception;

import com.moviemang.coreutils.common.response.ErrorCode;

public class EmptyException extends BaseException{

    public EmptyException() {
        super(ErrorCode.COMMON_EMPTY_DATA);
    }

    public EmptyException(String message) {
        super(message,ErrorCode.COMMON_EMPTY_DATA);
    }

    public EmptyException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public EmptyException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}
