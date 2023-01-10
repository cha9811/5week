package com.forum.forum.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;    //
    private String role;        //권한 확인
}
