package com.zhugs.auth.model.dto;

import lombok.Data;

@Data
public class LoginDto {

    private String username;

    private String password;

    private String accessToken;

    private String refreshToken;

}
