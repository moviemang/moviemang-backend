package com.moviemang.coreutils.common.exception;

import com.moviemang.coreutils.common.response.ErrorCode;

@SuppressWarnings("serial")
public class IllegalStatusException extends BaseException {
	
	public IllegalStatusException() {
        super(ErrorCode.COMMON_ILLEGAL_STATUS);
    }

    public IllegalStatusException(String message) {
        super(message, ErrorCode.COMMON_ILLEGAL_STATUS);
    }
	
}
