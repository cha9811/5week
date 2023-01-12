package com.forum.forum.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forum.forum.dto.SecurityExceptionDto;
import io.jsonwebtoken.Claims;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);   //토큰을 가져옴

        if(token != null) {     //헤더에 토큰이 있는지 없는지 점검
            if(!jwtUtil.validateToken(token)){      //토큰이 없다면 jwrutil의 토큰과 비교
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            Claims info = jwtUtil.getUserInfoFromToken(token);
            setAuthentication(info.getSubject());
        }
        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String username) {    //유저 id를 받으면
        SecurityContext context = SecurityContextHolder.createEmptyContext();   //
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    //에러 출력용 코드
    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) { //에러코드, 에러메세지, 에러코드 파마리터
        response.setStatus(statusCode); //코드를 넣고
        response.setContentType("application/json");    //json형태로 받환받기
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg)); //에러코드 및 에러메세지 받기
            response.getWriter().write(json);           //getWriter
        } catch (Exception e) {     //에러 발생시
            log.error(e.getMessage());
        }
    }

}