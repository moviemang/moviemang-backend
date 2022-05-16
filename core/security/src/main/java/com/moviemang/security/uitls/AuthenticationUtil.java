package com.moviemang.security.uitls;

import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.security.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationUtil {

    @Autowired
    private AuthenticationService authenticationService;

    public <T extends CommonParam> void checkAuthenticationInfo(HttpServletRequest httpServletRequest, T param) {

        String accessToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (StringUtils.isNoneEmpty(accessToken)){
            if (AuthenticationService.validateToken(accessToken, httpServletRequest)) {
                CommonParam commonParam = authenticationService.getCommonParam(accessToken);
                param.setEmail(commonParam.getEmail());
                param.setId(commonParam.getId());
            }
        }
    }
}
