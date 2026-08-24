package com.zhugs.common.constant;

public class RedisConstant {

    /**
     * 登录用户前缀
     */
    public static final String USER_LOGIN_PREFIX = "user:login:";

    /**
     * 登录用户超时时间 单位：天
     */
    public static final int USER_LOGIN_REDIS_TTL_DAY = 1;

    /**
     * 登录验证码超时时间 单位：秒
     */
    public static final int USER_LOGIN_CAPTCHA_TTL_SEC = 60;

    /**
     * 字典前缀
     */
    public static final String DICT_PREFIX = "dict:";


}
