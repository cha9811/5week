package com.forum.forum.service;

import com.forum.forum.dto.LoginRequest;
import com.forum.forum.dto.RegisterRequest;
import com.forum.forum.dto.StatusResponse;
import com.forum.forum.entity.Role;
import com.forum.forum.entity.User;
import com.forum.forum.jwt.JwtUtil;
import com.forum.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;          //JWT DI
    private final UserRepository userRepository;        //DI

    @Transactional      //회원가입
    public StatusResponse register(RegisterRequest registerRequest){    //id,pw 유효성 검사
        String username = registerRequest.getUsername();    //신규 아이디 받아오기

        Optional<User> found = userRepository.findByUsername(username);     //신규 있는지 찾기 (없어도 문제 없도록 optional 사용)
        if (found.isPresent()) {throw new IllegalArgumentException("중복된 사용자가 존재합니다.");}     //

        User user = registerRequest.toEntity();             //저장하기

        userRepository.save(user);                          //회원가입성공(저장하기)

        return new StatusResponse("회원가입 완료", HttpStatus.OK.value());    //성공문출력, 200코드 출력
    }



    @Transactional      //로그인
    public StatusResponse login(LoginRequest loginRequest, HttpServletResponse response){
        String username = loginRequest.getUsername();   //권한 가져오기
        String password = loginRequest.getPassword();   //권한 가져오기
        String role = loginRequest.getRole();   //권한 가져오기
        //닉네임 있는지 확인
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        //비번 맞는지 확인
        if(!user.getPassword().equals(password)){throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");}
        //토큰 만들기
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(),user.getRole()));
        if(!user.getRole().equals(Role.USER)){throw new IllegalArgumentException("현재 계정은 일반계정입니다 관리자 계정으로 로그인해주세요");}  //

        return new StatusResponse("로그인 완료", HttpStatus.OK.value());     //성공문 출력, 200코드 출력
    }
}
