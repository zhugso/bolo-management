package com.zhugs.common.cache.service.impl;

import com.zhugs.common.cache.service.CacheService;
import com.zhugs.common.core.util.JsonUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
public class RedisCacheServiceImpl implements CacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public <T> T get(String key, Class<T> type) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (Objects.isNull(value)) {
            return null;
        }
        return JsonUtil.toObject(value, type);
    }

    @Override
    public <T> T get(String key, Class<T> type, Duration ttl) {
        String value = stringRedisTemplate.opsForValue().getAndExpire(key, ttl);
        if (Objects.isNull(value)) {
            return null;
        }
        return JsonUtil.toObject(value, type);
    }

    @Override
    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JsonUtil.toJson(value));
    }

    @Override
    public void set(String key, Object value, Duration ttl) {
        stringRedisTemplate.opsForValue().set(key, JsonUtil.toJson(value), ttl);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void delete(String... keys) {
        stringRedisTemplate.delete(List.of(keys));
    }

    @Override
    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    @Override
    public boolean expire(String key, Duration ttl) {
        return stringRedisTemplate.expire(key, ttl);
    }
}
