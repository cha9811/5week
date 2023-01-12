package com.forum.forum.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {    //필터체인 ,예외처리
        // CSRF 설정(크로스체크)
        http.csrf().disable();

        http.authorizeHttpRequests().antMatchers().permitAll()
                .antMatchers("/**").permitAll()         //해당 리소스 접근은 인증절차 없이 허용한다,
                .antMatchers("/api/forum").permitAll() //글보는건 누구나 가능
                //antMatchers - 인증 필요함, permitAll - 무조건 접근 허용
                //anyRequests - 권한 필요없음
                //public antMatchers(String... antPatterns) {
                //		Assert.state(!this.anyRequestConfigured, "Can't configure antMatchers after anyRequest");
                //		return chainRequestMatchers(RequestMatchers.antMatchers(antPatterns));
                //	}
                //  .requestMatchers("/api/shop").permitAll()
                .anyRequest().authenticated();

        http.formLogin().loginPage("/api/user/login-page").permitAll(); //로그인페이지 연결

        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");   //에러 발생시 해당 페이지로연결

        return http.build();
    }

}