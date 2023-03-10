package com.forum.forum.jwt;

import com.forum.forum.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String AUTHORIZATION_KEY = "auth";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;   //비밀번호
    private Key key;            //키 비교용 임시변수
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //256암호화

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);   //암호화 풀기
        key = Keys.hmacShaKeyFor(bytes);        //임시변수에 넣기
    }

    // header 토큰을 가져오기
    //HttpServletRequest는 단순 서버에러 보이기
    public String resolveToken(HttpServletRequest request) {    //resolveToken 토큰추출하기
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);   //
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);    //앞자리 7개만 비교하기
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username, Role role) {
        Date date = new Date();     //날짜 생성용 임시 변수

        return BEARER_PREFIX +
                Jwts.builder()              //bearer 형태로 jwt생성
                        .setSubject(username)           //유저닉네임 받기
                        .claim(AUTHORIZATION_KEY, role) //키에 따른 권한 만들기
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))   //토큰 생성시간 확인하기
                        .setIssuedAt(date)              //생성시간 넣기
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String username) {
        UserDetails userDetails = memberDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}


