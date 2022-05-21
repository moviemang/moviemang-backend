package com.moviemang.member.config;

import com.moviemang.security.interceptor.AuthorityCheckInterceptor;
import com.moviemang.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan(basePackages = "com.moviemang")
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private AuthenticationService authenticationService;

    @Autowired
    public WebConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthorityCheckInterceptor authorityCheckInterceptor = new AuthorityCheckInterceptor(authenticationService);
        authorityCheckInterceptor.setAuthenticationService(authenticationService);
        registry.addInterceptor(authorityCheckInterceptor)
                .excludePathPatterns("/member/join","/token/refresh","/member/emailcheck","/member/nickcheck", "/cms/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("headers");
    }
}
