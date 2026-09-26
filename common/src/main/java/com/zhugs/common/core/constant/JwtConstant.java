package com.zhugs.common.core.constant;

public class JwtConstant {

    /**
     * jwt secretKey
     */
    public static final String SECRET = "WkDrRGtwpEShcVuNdGaHfpGZ33xxTSMz";

    /**
     * jwt 过期时间， 单位：分钟
     */
    public static final long JWT_EXPIRATION_TEN_MINUTE = 1000 * 60 * 10;

    /**
     * jwt 过期时间， 单位：秒
     */
    public static final long JWT_EXPIRATION_THREE_SECOND = 1000 * 3;

    /**
     *  jwt 最大长度
     */
    public static final int JWT_BYTE_SIZE_MAX = 2048;


}
