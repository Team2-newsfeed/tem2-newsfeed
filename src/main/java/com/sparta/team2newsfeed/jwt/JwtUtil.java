package com.sparta.team2newsfeed.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    // 직접 JwtUtil 을 생성하는 유틸을 모아두기

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey properties에 입력한값.
    private String secretKey;

    // 키 만들때 쓸 암호화 알고리즘
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // build.gradle 의  JWT 기능을 넣어야 사용가능

    //암호화된 키값
    private Key key;

    // 키값을 이 BEAN 이 생성될 때 같이 만들어 줄 수 있도록 @PostConstruct 사용
    // runtime 에서 bean 이 객체를 생성할때 한번 돌림
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith((BEARER_PREFIX))){
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            // key 값을 우리가 만들어 놓은걸 넣고, 토큰을 넣어주면 됨.
            return true;
            // 이상한 토큰이거나 하면 에러가 발생할거임 에러 발생 안했으면 그냥 true
            // 발생하면 캐치문
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String createToken(String username) {
        Date date = new Date();

        // 토큰 만료시간 60분
        long TOKEN_TIME = 60*60*1000 ;
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(date.getTime()+TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key,signatureAlgorithm)
                .compact();
    }
}