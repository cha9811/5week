package com.forum.forum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class SecurityExceptionDto {

    private int statusCode; //에러코드
    private String msg;     //에러메세지 출력용

    public SecurityExceptionDto(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}