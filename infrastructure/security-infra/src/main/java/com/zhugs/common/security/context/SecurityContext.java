package com.zhugs.common.security.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityContext {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String key, Object value) {
        getLocalMap().put(key, Objects.isNull(value) ? "" : value);
    }

    public static <T> T get(String key, Class<T> tClass) {
        Object o = getLocalMap().get(key);
        if (tClass.isInstance(o)) {
            return tClass.cast(o);
        } else {
            throw new ClassCastException("目标类型非给定类型: " + tClass);
        }
    }

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (Objects.isNull(map)) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }

}
