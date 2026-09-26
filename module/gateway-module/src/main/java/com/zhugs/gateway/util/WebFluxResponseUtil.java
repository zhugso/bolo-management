package com.zhugs.gateway.util;

import com.zhugs.common.core.util.JsonUtil;
import com.zhugs.common.core.util.R;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class WebFluxResponseUtil {

    public static Mono<Void> writeJson(ServerWebExchange exchange, int code, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(
                new MediaType("application", "json", StandardCharsets.UTF_8)
        );

        String body = JsonUtil.toJson(R.custom(code, msg));

        DataBuffer buffer = response.bufferFactory()
                .wrap(body.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }
}