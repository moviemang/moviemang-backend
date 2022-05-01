package com.moviemang.coreutils.common.exception;

import com.moviemang.coreutils.common.response.ErrorCode;


public class AuthenticationException extends javax.security.sasl.AuthenticationException {
    private ErrorCode errorCode;

    public AuthenticationException(){

    }


    public AuthenticationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public AuthenticationException(String detail, ErrorCode errorCode) {
        super(detail);
        this.errorCode = errorCode;
    }

    public AuthenticationException(String detail, Throwable ex, ErrorCode errorCode) {
        super(detail, ex);
        this.errorCode = errorCode;
    }
}
