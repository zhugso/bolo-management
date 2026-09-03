package com.zhugs.file.infrastructure.storage;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.paginators.ListBucketsIterable;

@Slf4j
@Component
public class S3Init implements ApplicationRunner {

    private final S3Client s3Client;

    private final S3Properties properties;


    public S3Init(S3Client s3Client, S3Properties properties) {
        this.s3Client = s3Client;
        this.properties = properties;
    }

    // 桶初始化
    @Override
    public void run(@NonNull ApplicationArguments args) {

        String bucketName = properties.getBucketName();

        ListBucketsIterable response = s3Client.listBucketsPaginator();
        boolean exists = response.buckets().stream().map(Bucket::name).anyMatch(bucketName::equals);
        if (!exists) {
            log.info("bucket: {}, 不存在; 创建", bucketName);
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(properties.getBucketName())
                    .build();
            s3Client.createBucket(bucketRequest);
        } else {
            log.info("bucket: {}, 已存在。", bucketName);
        }

    }
}
