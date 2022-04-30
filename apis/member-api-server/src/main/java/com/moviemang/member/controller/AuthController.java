package com.moviemang.member.controller;


import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.member.domain.RefreshToken;
import com.moviemang.security.domain.TokenInfo;
import com.moviemang.security.service.AuthenticationService;
import com.moviemang.security.service.UserDetailServiceImpl;
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

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @PostMapping(value = "/token/refresh")
    public CommonResponse refreshToken(HttpServletRequest request, @RequestBody RefreshToken refreshToken) {
        TokenInfo tokenInfo;

        try {
            authenticationService = new AuthenticationService(userDetailsService);
            tokenInfo = authenticationService.refreshAccessToken(request, refreshToken.getRefreshToken());

        } catch (Exception e) {
            return CommonResponse.fail(ErrorCode.AUTH_REFRESH_TOKEN_INVALID);
        }

        return CommonResponse.success(tokenInfo);


    }
}
