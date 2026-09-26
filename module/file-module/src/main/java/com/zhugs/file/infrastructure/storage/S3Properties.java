package com.zhugs.file.infrastructure.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file.storage.s3")
public class S3Properties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
    private String prefix = "files";

    public boolean isConfigured() {
        return endpoint != null && accessKey != null && secretKey != null && bucketName != null;
    }
}
