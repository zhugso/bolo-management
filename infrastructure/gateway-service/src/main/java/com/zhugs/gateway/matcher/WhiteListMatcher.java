package com.zhugs.gateway.matcher;

import com.zhugs.gateway.properties.SecurityProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Component
public class WhiteListMatcher {

    private final List<String> patterns;
    private final AntPathMatcher matcher = new AntPathMatcher();

    public WhiteListMatcher(SecurityProperties properties) {
        this.patterns = properties.getWhitelist();
    }

    public boolean match(String path) {
        for (String pattern : patterns) {
            if (matcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }
}