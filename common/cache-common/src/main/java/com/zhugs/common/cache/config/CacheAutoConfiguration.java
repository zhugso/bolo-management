package com.zhugs.common.cache.config;

import com.zhugs.common.cache.service.CacheService;
import com.zhugs.common.cache.service.impl.RedisCacheServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CacheAutoConfiguration {

    @Bean
    public CacheService redisService() {
        return new RedisCacheServiceImpl();
    }
}