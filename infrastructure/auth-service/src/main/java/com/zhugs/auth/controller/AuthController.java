package com.zhugs.auth.controller;

import com.zhugs.auth.model.convert.LoginConvert;
import com.zhugs.auth.model.convert.LoginReqConvert;
import com.zhugs.auth.model.dto.LoginDto;
import com.zhugs.auth.model.vo.LoginReqVo;
import com.zhugs.auth.model.vo.LoginVo;
import com.zhugs.auth.service.AuthService;
import com.zhugs.common.core.util.R;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    private final LoginReqConvert loginReqConvert;

    private final LoginConvert loginConvert;

    @PostMapping("login")
    public R<LoginVo> login(HttpServletResponse response, @RequestBody LoginReqVo vo) {
        LoginDto dto = loginReqConvert.convert(vo);
        LoginDto login = authService.login(dto);
        response.setHeader("AccessToken", login.getAccessToken());
        response.setHeader("RefreshToken", login.getRefreshToken());
        return R.ok(loginConvert.convert(login));
    }

    @PostMapping("logout")
    public R<?> logout(HttpServletResponse response) {
        authService.logout(response.getHeader("Authorization"));
        return R.ok();
    }

    @PostMapping("refresh")
    public R<LoginVo> refresh(HttpServletResponse response, @RequestHeader("Authorization") String refreshToken) {
        LoginDto token = authService.refresh(refreshToken);
        response.setHeader("AccessToken", token.getAccessToken());
        response.setHeader("RefreshToken", token.getRefreshToken());
        return R.ok(loginConvert.convert(token));
    }

}
