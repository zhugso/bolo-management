package com.zhugs.auth.model.vo;

import lombok.Data;

@Data
public class LoginVo {

    private String accessToken;

    private String refreshToken;

}
