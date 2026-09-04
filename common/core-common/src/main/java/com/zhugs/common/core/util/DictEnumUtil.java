package com.zhugs.common.core.util;

public final class DictEnumUtil {

    public static <T extends Enum<T> & DictEnum> T of(Class<T> enumClass, String code) {
        for (T e : enumClass.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        throw new IllegalArgumentException("非法字典值：" + code);
    }

}