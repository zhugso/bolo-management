package com.zhugs.common.security.util;


import com.zhugs.common.core.constant.SecurityConstant;
import com.zhugs.common.security.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SecurityUtil {

    private final static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private SecurityUtil() {
    }

    public static String encryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    static void main() {
        System.out.println(encryptPassword("Aa123456!"));
    }

    public static boolean match(String raw, String encoded) {
        return bCryptPasswordEncoder.matches(raw, encoded);
    }

    public static long getId() {
        Object userId = SecurityContext.getLocalMap().get(SecurityConstant.DEFAULT_USER_ID);
        return Long.parseLong(userId.toString());
    }

    public static String getUsername() {
        Object userId = SecurityContext.getLocalMap().get(SecurityConstant.DEFAULT_USERNAME);
        return userId.toString();
    }
    public static long getDeptId() {
        Object deptId = SecurityContext.getLocalMap().get(SecurityConstant.DEFAULT_DEPT_ID);
        return Long.parseLong(deptId.toString());
    }
    public static long getPostId() {
        Object postId = SecurityContext.getLocalMap().get(SecurityConstant.DEFAULT_POST_ID);
        return Long.parseLong(postId.toString());
    }

    public static Set<String> getRoles() {
        Object roles = SecurityContext.getLocalMap().get(SecurityConstant.DEFAULT_ROLES);
        return new HashSet<String>((Collection) roles);
    }

}
