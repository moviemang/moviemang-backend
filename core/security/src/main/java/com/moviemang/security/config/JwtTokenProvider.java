package com.moviemang.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class JwtTokenProvider {
    static final long EXPIRATIONTIME = 30*60*100L;   //토큰 유효시간 : 30분
    static String SIGNINGKEY = "moviemang-key";
    static final String BEARER_PREFIX = "Bearer";

    // 의존성 주입 후, 초기화를 수행
    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        SIGNINGKEY = Base64.getEncoder().encodeToString(SIGNINGKEY.getBytes());
    }

    // JWT Token 생성.
    public String createToken(String user, List<String> roles){
        Claims claims = Jwts.claims().setSubject(user); // claims 생성 및 payload 설정
        claims.put("roles", roles); // 권한 설정, key/ value 쌍으로 저장

        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + EXPIRATIONTIME)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, SIGNINGKEY) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }
}
