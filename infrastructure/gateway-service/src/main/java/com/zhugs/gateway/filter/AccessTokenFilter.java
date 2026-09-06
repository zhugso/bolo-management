package com.zhugs.gateway.filter;

import com.zhugs.common.core.constant.HeaderConstant;
import com.zhugs.common.core.constant.SecurityConstant;
import com.zhugs.common.core.util.JwtUtil;
import com.zhugs.gateway.matcher.WhiteListMatcher;
import com.zhugs.gateway.util.WebFluxResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@NullMarked
@Slf4j
public class AccessTokenFilter implements WebFilter {

    private final WhiteListMatcher whiteListMatcher;

    public AccessTokenFilter(WhiteListMatcher whiteListMatcher) {
        this.whiteListMatcher = whiteListMatcher;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // 跳过认证白名单
        String path = exchange.getRequest().getURI().getPath();
        if (whiteListMatcher.match(path)) {
            return chain.filter(exchange);
        }

        ServerHttpRequest request = exchange.getRequest();

        String authorization = request.getHeaders().getFirst(SecurityConstant.HEADER);

        if (Objects.isNull(authorization) || !authorization.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            return unauthorized(exchange, "用户未登录或登录已过期");
        }

        authorization = authorization.replaceFirst(SecurityConstant.TOKEN_PREFIX, "");
        log.info("{}", authorization);

        Map<String, ?> userInfo;
        try {
            userInfo = JwtUtil.parseToken(authorization);
        } catch (RuntimeException e) {
            return unauthorized(exchange, "用户未登录或登录已过期");
        }

        String userId = Optional.ofNullable(userInfo.get("userId")).toString();
        String username = Optional.ofNullable(userInfo.get("username")).toString();

        ServerHttpRequest mutatedRequest = request.mutate()
                .header(HeaderConstant.X_USER_ID, userId)
                .header(HeaderConstant.X_USER_NAME, username)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        return chain.filter(mutatedExchange);
    }


    private Mono<Void> unauthorized(ServerWebExchange exchange, String msg) {
        return WebFluxResponseUtil.writeJson(exchange, HttpStatus.UNAUTHORIZED.value(), msg);
    }

}
