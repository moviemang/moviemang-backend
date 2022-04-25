package com.moviemang.member.controller;


import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.member.domain.RefreshToken;
import com.moviemang.security.domain.TokenInfo;
import com.moviemang.security.service.AuthenticationService;
import com.sun.org.apache.xml.internal.security.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/token/refresh")
    public CommonResponse refreshToken(HttpServletRequest request, @RequestBody RefreshToken refreshToken) {
        String authorizationHeader = request.getHeader("Authorization");
        TokenInfo tokenInfo;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try{
                authenticationService = new AuthenticationService();
                tokenInfo = authenticationService.refreshAccessToken(request, refreshToken.getAccessToken());

            }catch (Exception e) {
                return CommonResponse.fail(ErrorCode.AUTH_INVALID_JWT);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }

        return CommonResponse.success(tokenInfo);


    }
}
