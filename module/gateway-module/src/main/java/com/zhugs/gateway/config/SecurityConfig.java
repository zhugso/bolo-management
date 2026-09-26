package com.zhugs.gateway.config;

import com.zhugs.gateway.filter.AccessTokenFilter;
import com.zhugs.gateway.matcher.WhiteListMatcher;
import com.zhugs.gateway.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;


@EnableConfigurationProperties(SecurityProperties.class)
@EnableReactiveMethodSecurity
@Configuration
public class SecurityConfig {

    private final WhiteListMatcher whiteListMatcher;

    public SecurityConfig(WhiteListMatcher whiteListMatcher) {
        this.whiteListMatcher = whiteListMatcher;
    }

    @Bean
    public SecurityWebFilterChain loginFilterChain(ServerHttpSecurity http) throws Exception {
        http
                // 关闭一些无用的设置
                // csrf防护
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // 无状态
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                // form表单登录
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                // httpBasic认证
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                // requestCache用于重定向，前后端分离项目无需重定向，requestCache也用不上
                .requestCache(ServerHttpSecurity.RequestCacheSpec::disable)
                // 默认的注销接口(/logout),不关闭会占用(/logout)接口,使无法跳转到自己的接口
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                // 匿名认证
                .anonymous(ServerHttpSecurity.AnonymousSpec::disable)
                // 允许跨域
                .cors(Customizer.withDefaults())
                // 安全头
                .headers(header -> header
                        .contentSecurityPolicy(
                                cps -> cps
                                        .policyDirectives("default-src 'self'")))

                // 所有请求都通过，不需要security来处理，我们自己的过滤器链来处理
                .authorizeExchange(exchange -> exchange.anyExchange().permitAll())
                // 替换security的认证过滤器为我们自己的
                .addFilterAt(new AccessTokenFilter(whiteListMatcher), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }


}
