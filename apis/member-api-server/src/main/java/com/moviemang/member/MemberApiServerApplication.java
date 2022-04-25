package com.moviemang.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com.moviemang")
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MemberApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApiServerApplication.class, args);
    } 
}