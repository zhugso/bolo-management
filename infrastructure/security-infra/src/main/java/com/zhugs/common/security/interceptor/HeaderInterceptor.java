package com.zhugs.common.security.interceptor;


import com.zhugs.common.core.constant.HeaderConstant;
import com.zhugs.common.core.constant.SecurityConstant;
import com.zhugs.common.security.context.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


public class HeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader(HeaderConstant.X_USER_ID);
        String username = request.getHeader(HeaderConstant.X_USER_NAME);
        String deptId = request.getHeader(HeaderConstant.X_DEPT_ID);
        String postId = request.getHeader(HeaderConstant.X_POST_ID);
        Enumeration<String> roles = request.getHeaders(HeaderConstant.X_ROLES);
        List<String> roleList = Collections.list(roles);

        SecurityContext.set(SecurityConstant.DEFAULT_USER_ID, userId);
        SecurityContext.set(SecurityConstant.DEFAULT_USERNAME, username);
        SecurityContext.set(SecurityConstant.DEFAULT_DEPT_ID, deptId);
        SecurityContext.set(SecurityConstant.DEFAULT_POST_ID, postId);
        SecurityContext.set(SecurityConstant.DEFAULT_ROLES, roleList);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        SecurityContext.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
