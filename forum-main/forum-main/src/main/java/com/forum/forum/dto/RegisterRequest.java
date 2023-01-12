package com.forum.forum.dto;

import com.forum.forum.entity.Role;
import com.forum.forum.entity.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class RegisterRequest {
    @NotBlank(message="아이디를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9a-z]).{4,10}",message = "아이디는 4~10자 영문 소문자, 숫자를 이용해주세요.")
    private String username;

    @NotBlank(message="비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}", message = "비밀번호는 8~15자 영문 대 소문자 및 숫자를 이용해주세요.")   //유효성검사
    private String password;
    @NotNull
    private Role role;      //권한 받아오기

    public User toEntity(){         //값 받아오기
        return User.builder().username(username).password(password).role(role).build();
    }
}