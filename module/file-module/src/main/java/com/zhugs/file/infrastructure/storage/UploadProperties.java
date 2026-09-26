package com.zhugs.file.infrastructure.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file.upload")
public class UploadProperties {

    private Long maxSize;

    private Long partSize;

    private Long expireHours;

}
