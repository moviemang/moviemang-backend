package com.moviemang.member.controller;


import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.member.dto.RefreshToken;
import com.moviemang.security.domain.TokenInfo;
import com.moviemang.security.service.AuthenticationService;
import com.moviemang.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @PostMapping(value = "/token/refresh")
    public ResponseEntity refreshToken(HttpServletRequest request, @RequestBody RefreshToken refreshToken) {
        TokenInfo tokenInfo;

        try {
            authenticationService = new AuthenticationService(userDetailsService);
            tokenInfo = authenticationService.refreshAccessToken(request, refreshToken.getRefreshToken());

        } catch (Exception e) {
            return ResponseEntity.status(401).body(ErrorCode.AUTH_REFRESH_TOKEN_INVALID);
        }

        return ResponseEntity.ok(tokenInfo);


    }
}
