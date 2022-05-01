package com.moviemang.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        try (OutputStream os = response.getOutputStream()){

            CommonResponse commonResponse = CommonResponse.fail(ErrorCode.AUTH_INVALID_JWT,HttpStatus.UNAUTHORIZED);

//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.writeValue(os, commonResponse);
//            os.flush();
            new ObjectMapper().writeValue(response.getOutputStream(), commonResponse);
        }
    }
}
