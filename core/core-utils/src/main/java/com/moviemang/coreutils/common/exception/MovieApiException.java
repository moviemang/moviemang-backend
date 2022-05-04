package com.moviemang.coreutils.common.exception;

import com.moviemang.coreutils.common.response.ErrorCode;

public class MovieApiException extends BaseException {

    public MovieApiException(ErrorCode errorCode){ super(errorCode);}

    public MovieApiException(String message, ErrorCode errorCode){super(message, errorCode);}
}
