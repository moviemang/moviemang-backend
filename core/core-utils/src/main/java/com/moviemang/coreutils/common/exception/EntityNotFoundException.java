package com.moviemang.coreutils.common.exception;

import com.moviemang.coreutils.common.response.ErrorCode;

@SuppressWarnings("serial")
public class EntityNotFoundException extends BaseException {
	
	public EntityNotFoundException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.COMMON_INVALID_PARAMETER);
    }
}
