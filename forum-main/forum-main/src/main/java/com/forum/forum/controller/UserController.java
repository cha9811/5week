package com.forum.forum.controller;

import com.forum.forum.dto.LoginRequest;
import com.forum.forum.dto.RegisterRequest;
import com.forum.forum.dto.StatusResponse;
import com.forum.forum.entity.Role;
import com.forum.forum.entity.User;
import com.forum.forum.repository.UserRepository;
import com.forum.forum.security.UserDetailsImpl;
import com.forum.forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
//    private static final String ADMIN_TOKEN = "7ZWt7ZW0OTntmZTsnbTtjIXtlZzqta3snYTrhIjrqLjshLjqs4TroZzrgpjslYTqsIDsnpDtm4zrpa3tlZzqsJzrsJzsnpDrpbzrp4zrk6TslrTqsIDsnpA=";

    @PostMapping("/register")       //회원가입
    public ResponseEntity<StatusResponse> register(@Valid @RequestBody RegisterRequest registerRequest, @AuthenticationPrincipal UserDetailsImpl memberDetails){    //값은 body에서 가져오기
        StatusResponse user = userService.register(registerRequest);                                        //에러메세지 = 출력
        return new ResponseEntity<>(user, HttpStatus.valueOf(user.getStatusCode()));                        //에러코드 출력(여기선 정상 작동시 200)
    }

    //public ResponseEntity(@Nullable T body, HttpStatus status) {
    //		this(body, null, status);
    //	}

    @PostMapping("/login")          //로그인
    public ResponseEntity<StatusResponse> login(@RequestBody LoginRequest loginRequest,HttpServletResponse response){   //값은 body에서 가져오기,
        StatusResponse user = userService.login(loginRequest, response);                                                //에러메세지 = 출력
        return new ResponseEntity<>(user, HttpStatus.valueOf(user.getStatusCode()));
    }




    ///회원가입용(셀렉샵 코드)
//    public String signup(RegisterRequest registerRequest) {
//
//        String username = registerRequest.getUsername();
//        String password = passwordEncoder.encode(registerRequest.getPassword());
//
//        // 회원 중복 확인
//        Optional<User> found = userRepository.findByUsername(username);
//        if (found.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
//        }
//
//        // 사용자 ROLE 확인
//        Role role = Role.USER;
//        if (registerRequest.isAdmin()) {
//            if (!registerRequest.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = Role.ADMIN;
//        }
//
//        User user = new User(username, password, role);
//        userRepository.save(user);
//
//        return "redirect:/api/user/login-page";
//    }

}
