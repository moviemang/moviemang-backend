package com.moviemang.playlist.config;

import com.moviemang.security.interceptor.AuthorityCheckInterceptor;
import com.moviemang.security.service.AuthenticationService;
import com.moviemang.security.uitls.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        registry.addInterceptor(authorityCheckInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("headers");
    }
}
