package com.zhugs.common.cache.service;

import java.time.Duration;

public interface CacheService {

    <T> T get(String key, Class<T> type);

    <T> T get(String key, Class<T> type, Duration ttl);

    void set(String key, Object value);

    void set(String key, Object value, Duration ttl);

    void delete(String key);

    void delete(String... keys);

    boolean exists(String key);

    long increment(String key);

    boolean expire(String key, Duration ttl);
}