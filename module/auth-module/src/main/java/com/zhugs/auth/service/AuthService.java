package com.zhugs.auth.service;

import com.zhugs.auth.model.dto.LoginDto;
import com.zhugs.auth.model.dto.UserDetailsDto;
import com.zhugs.common.cache.service.CacheService;
import com.zhugs.common.core.constant.RedisConstant;
import com.zhugs.common.core.exception.AuthenticationException;
import com.zhugs.common.core.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final CacheService cacheService;


    public LoginDto login(LoginDto dto) {
        Authentication authenticate = null;

        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );
        } catch (RuntimeException e) {
            throw new AuthenticationException(e.getMessage());
        }
        UserDetailsDto principal = (UserDetailsDto) authenticate.getPrincipal();

        log.info("[login]: {}", principal);

        assert principal != null;

//        if (SysStatus.Disabled == DictEnumUtil.of(SysStatus.class, principal.getStatus())) {
//            throw new AuthenticationException("帐户已停用，请联系管理员！");
//        }

        long userId = principal.getId();
        String username = principal.getUsername();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("username", username);

        String accessToken = JwtUtil.createToken("user", map);
        if (JwtUtil.isJwtTooLarge(accessToken)) {
            throw new RuntimeException();
        }

        log.info("[login]: {}", accessToken);
        String refreshToken = UUID.randomUUID().toString();
        log.info("[login]: {}", refreshToken);
        String redisKey = RedisConstant.USER_LOGIN_PREFIX + refreshToken;
        UserInfoRedis redisValue = new UserInfoRedis(userId, username);
        cacheService.set(redisKey, redisValue, Duration.ofDays(RedisConstant.USER_LOGIN_REDIS_TTL_DAY));

        LoginDto loginDto = new LoginDto();
        loginDto.setAccessToken(accessToken);
        loginDto.setRefreshToken(refreshToken);
        return loginDto;
    }

    public void logout(String refreshToken) {
        if (Objects.isNull(refreshToken) || refreshToken.isEmpty()) {
            return;
        }
        String redisKey = RedisConstant.USER_LOGIN_PREFIX + refreshToken.substring(7);
        cacheService.delete(redisKey);
    }

    public LoginDto refresh(String refreshToken) {
        refreshToken = refreshToken.substring(7);
        String redisKey = RedisConstant.USER_LOGIN_PREFIX + refreshToken;

        UserInfoRedis userinfo = cacheService.get(redisKey, UserInfoRedis.class);

        if (Objects.isNull(userinfo)) {
            throw new AuthenticationException("用户未登录或登录已过期");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userinfo.id());
        map.put("username", userinfo.username());
        String accessToken = JwtUtil.createToken("user", map);

        if (JwtUtil.isJwtTooLarge(accessToken)) {
            throw new RuntimeException();
        }

        LoginDto loginDto = new LoginDto();
        loginDto.setAccessToken(accessToken);
        loginDto.setRefreshToken(refreshToken);
        return loginDto;
    }


    record UserInfoRedis(
            long id,
            String username
    ) {
    }


}

