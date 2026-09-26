package com.zhugs.common.core.util;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {
    }

    public static Map<String, Object> toMap(String json) {
        return objectMapper.readValue(json, new TypeReference<>() {
        });
    }

    public static String toJson(Object obj) {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> T mapToObject(Object o, Class<T> clazz) {
        return objectMapper.convertValue(o, clazz);
    }


}
