package com.zhugs.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    /**
     * 白名单路径
     */
    private List<String> whitelist = new ArrayList<>();

}