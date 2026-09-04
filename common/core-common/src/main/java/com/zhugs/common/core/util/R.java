package com.zhugs.common.core.util;


import lombok.Getter;


@Getter
public class R<T> {
    private final int code;
    private final String message;
    private final T data;

    private R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 200 ok
    public static R<?> ok() {
        return new R<>(200, "success", null);
    }

    // 200 ok with data
    public static <T> R<T> ok(T data) {
        return new R<>(200, "success", data);
    }

    // error
    public static R<?> error() {
        return new R<>(500, "error", null);
    }

    // error with message
    public static R<?> error(String message) {
        return new R<>(500, message, null);
    }

    // custom status code
    public static R<?> custom(int code, String message) {
        return new R<>(code, message, null);
    }

    public static R<?> from(int i) {
        if (i > 0) {
            return ok();
        } else {
            return error();
        }
    }

    public static R<?> from(boolean i) {
        if (i) {
            return ok();
        } else {
            return error();
        }
    }

}
