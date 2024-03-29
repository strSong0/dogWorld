package com.example.dogWorld.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
// JWT 관련 기능들을 넣어두기 위한 기능성 클래스
public class JwtTokenUtils {
    private final Key signingKey;
    private final JwtParser jwtParser;

    public JwtTokenUtils(
            @Value("${jwt.secret}")
            String jwtSecret
    ) {
        this.signingKey
                = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        // JWT 번역기 만들기
        this.jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(this.signingKey)
                .build();
    }

    // 1. JWT가 유효한지 판단하는 메소드
    //    jjwt 라이브러리에서는 JWT를 해석하는 과정에서
    //    유효하지 않으면 예외가 발생
    public boolean validate(String token) {
        try {
            // 정당한 JWT면 true,
            // parseClaimsJws: 암호화된 JWT를 해석하기 위한 메소드
            jwtParser.parseClaimsJws(token);
            return true;
            // 정당하지 않은 JWT면 false
        } catch (Exception e) {
            log.warn("invalid jwt: {}", e.getClass());
            return false;
        }
    }

    // JWT를 인자로 받고, 그 JWT를 해석해서
    // 사용자 정보를 회수하는 메소드
    public Claims parseClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    public String createAccessToken(String username) {
        Claims jwtClaims = Jwts.claims()
                // 사용자 정보 등록
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600 * 24)));
        return Jwts.builder()
                .setClaims(jwtClaims)
                .signWith(signingKey)
                .compact();
    }

    public JwtTokenInfoDto generateToken(String username) {
        JwtTokenInfoDto jwtTokenInfoDto = new JwtTokenInfoDto();
        String accessToken = this.createAccessToken(username);
        jwtTokenInfoDto.setToken(accessToken);
        return jwtTokenInfoDto;
    }

}
