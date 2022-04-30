package com.moviemang.security.config;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        CommonResponse commonResponse;
        String exceptionType = (String) request.getAttribute("exception");

        if ("invalidSignature".equalsIgnoreCase(exceptionType)) {
            commonResponse = CommonResponse.fail(ErrorCode.AUTH_INVALID_SIGNATURE_JWT);
        } else if ("invalidJwt".equalsIgnoreCase(exceptionType)) {
            commonResponse = CommonResponse.fail(ErrorCode.AUTH_MALFORMED_JWT);
        } else if ("expiredJwt".equalsIgnoreCase(exceptionType)) {
            commonResponse = CommonResponse.fail(ErrorCode.AUTH_EXPIRED_JWT);
        } else if ("claimsEmpty".equalsIgnoreCase(exceptionType)) {
            commonResponse = CommonResponse.fail(ErrorCode.AUTH_ILLEGAL_ARGUMENT_JWT);
        } else {
            commonResponse = CommonResponse.fail(ErrorCode.AUTH_INVALID_JWT);
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(commonResponse);
        response.getWriter().flush();
    }
}
