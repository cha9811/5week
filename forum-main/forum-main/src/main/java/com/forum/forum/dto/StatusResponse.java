package com.forum.forum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusResponse {
    private String message;     //메세지  (에러메세지)
    private int statusCode;     //상태코드 (에러코드)

    public StatusResponse(String massage, int statusCode) {
        this.message = massage;
        this.statusCode = statusCode;
    }


}
