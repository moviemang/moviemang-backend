package com.moviemang.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.coreutils.common.response.ErrorCode;
import com.moviemang.coreutils.model.vo.CommonParam;
import com.moviemang.datastore.entity.maria.Member;
import com.moviemang.datastore.repository.maria.MemberRepository;
import com.moviemang.security.domain.CustomMember;
import com.moviemang.security.domain.TokenInfo;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Collections.emptyList;

@Component
@Slf4j
public class AuthenticationService {

//    private static long REFRESH_TOKEN_VALID_SECONDS = 60 * 60 * 24 * 30; // default 30 days.
    private static long ACCESS_TOKEN_VALID_SECONDS = 60 * 60 * 12 * 1000L; // default 12 hours.
    private static long REFRESH_TOKEN_VALID_SECONDS = 60 * 60 * 24 * 7 * 1000L; // default 7일
//    private static long ACCESS_TOKEN_VALID_SECONDS = 3 * 60 * 1000L; // default 5minute
    static final String SIGNINGKEY = "moviemang-key";
    static final String BEARER_PREFIX = "Bearer";


    private UserDetailServiceImpl userDetailsService;

    @Autowired
    public AuthenticationService(UserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //    static public void creatJwtToken(HttpServletResponse response, String username) {
    public static void creatJwtToken(HttpServletResponse response, Authentication authentication) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");

        CustomMember customMember = (CustomMember) authentication.getPrincipal();
        TokenInfo tokenInfo = createToken(authentication.getName(),customMember.getId(), Arrays.asList("ROLE_USER"));


        new ObjectMapper().writeValue(response.getOutputStream(), tokenInfo);

    }

    public static TokenInfo createToken(String username, Long userNo, List<String> roles){
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("id", userNo);
        return TokenInfo.builder()
                .accessToken(BEARER_PREFIX + " " + generateToken(claims, new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_SECONDS)))
                .refreshToken(generateToken(claims, new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_SECONDS)))
                .build();
    }

    public static String generateToken(Claims claims, Date expirationDt) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expirationDt)
                .signWith(SignatureAlgorithm.HS256, SIGNINGKEY)
                .compact();
    }

    public static String createRefreshToken(String username){
        Claims claims = Jwts.claims();
        claims.put("username", username);
        String refreshJwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_SECONDS))
                .signWith(SignatureAlgorithm.HS256, SIGNINGKEY)
                .compact();
        return refreshJwtToken;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token != null) {
            String user = (String) Jwts.parser()
                    .setSigningKey(SIGNINGKEY)
                    .parseClaimsJws(token.replace(BEARER_PREFIX, ""))
                    .getBody()
                    .get("username");

            if (user != null && StringUtils.isNoneEmpty(user)) {
                return new UsernamePasswordAuthenticationToken(user, null, emptyList());
            } else {
                throw new RuntimeException("Authentication failed");
            }
        }
        return null;
    }

    public static Claims getUserPk(String token) {
        Claims cliam = Jwts.parser()
                .setSigningKey(SIGNINGKEY)
                .parseClaimsJws(token)
                .getBody();

        return cliam;
    }

    public CommonParam getCommonParam(String token) {

        Claims cliam = Jwts.parser()
                .setSigningKey(SIGNINGKEY)
                .parseClaimsJws(token)
                .getBody();

        Integer id = (Integer) cliam.get("id");
        return new CommonParam(){{
            setEmail((String) cliam.get("username"));
            setId(id.longValue());
        }};
    }

    public static String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public static boolean validateToken(String jwtToken, HttpServletRequest request) {

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SIGNINGKEY)
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException ex) {
            log.error("Invalid JWT Signature");
            request.setAttribute("exception", "invalidSignature");
            return false;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            request.setAttribute("exception", "invalidJwt");
            return false;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            request.setAttribute("exception", "expiredJwt");
            return false;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT exception");
            request.setAttribute("exception", "unsupportedJwt");
            return false;
        } catch (IllegalArgumentException ex) {
            log.error("Jwt claims string is empty");
            request.setAttribute("exception", "claimsEmpty");
            return false;
        }
    }

    public TokenInfo refreshAccessToken(HttpServletRequest request, String refreshToken){

        if (validateToken(refreshToken, request)) {
            String email = getUserPk(refreshToken).get("username",String.class);
            CustomMember member = (CustomMember) userDetailsService.loadUserByUsername(email);

            TokenInfo refreshAccessToken = createToken(member.getUsername(), member.getId(),Arrays.asList("ROLE_USER"));

            return refreshAccessToken;
        } else {
            throw new AuthenticationServiceException(ErrorCode.AUTH_REFRESH_TOKEN_INVALID.getErrorMsg());
        }
    }




}
