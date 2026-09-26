package com.zhugs.file.infrastructure.storage;

import com.zhugs.file.domain.storage.ObjectStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Primary
public class ObjectStorageImpl implements ObjectStorage {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final S3Properties properties;

    private static final Duration PRESIGN_URL_EXPIRATION_1 = Duration.ofHours(1);
    private static final Duration PRESIGN_URL_EXPIRATION_24 = Duration.ofHours(24);

    public ObjectStorageImpl(S3Client s3Client, S3Presigner s3Presigner, S3Properties properties) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.properties = properties;
    }

    @Override
    public String getPresignedUploadUrl(String bucket, String objectKey, Long fileSize, String contentType) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .contentType(contentType)
                    .contentLength(fileSize)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(PRESIGN_URL_EXPIRATION_1)
                    .putObjectRequest(putObjectRequest)
                    .build();

            String presignedUrl = s3Presigner.presignPutObject(presignRequest).url().toString();
            log.info("Generated presigned URL for upload: {}", objectKey);
            return presignedUrl;
        } catch (Exception e) {
            log.error("Failed to generate presigned URL for bucket: {}, objectKey: {}", bucket, objectKey, e);
            throw new RuntimeException("Failed to generate presigned URL", e);
        }
    }

    @Override
    public String initiateMultipartUpload(String bucket, String objectKey, String contentType) {
        try {
            CreateMultipartUploadRequest request = CreateMultipartUploadRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .contentType(contentType)
                    .build();

            CreateMultipartUploadResponse response = s3Client.createMultipartUpload(request);
            log.info("Initiated multipart upload for bucket: {}, objectKey: {}, uploadId: {}",
                    bucket, objectKey, response.uploadId());
            return response.uploadId();
        } catch (Exception e) {
            log.error("Failed to initiate multipart upload for bucket: {}, objectKey: {}", bucket, objectKey, e);
            throw new RuntimeException("Failed to initiate multipart upload", e);
        }
    }

    @Override
    public String getPresignedPartUploadUrl(String bucket, String objectKey, String uploadId, Integer partNumber) {
        try {
            UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .uploadId(uploadId)
                    .partNumber(partNumber)
                    .build();

            UploadPartPresignRequest presignRequest = UploadPartPresignRequest.builder()
                    .signatureDuration(PRESIGN_URL_EXPIRATION_1)
                    .uploadPartRequest(uploadPartRequest)
                    .build();

            String presignedUrl = s3Presigner.presignUploadPart(presignRequest).url().toString();
            log.info("Generated presigned URL for part upload: {}, part: {}", objectKey, partNumber);
            return presignedUrl;
        } catch (Exception e) {
            log.error("Failed to generate presigned URL for part upload: {}, part: {}", objectKey, partNumber, e);
            throw new RuntimeException("Failed to generate presigned part URL", e);
        }
    }

    @Override
    public void completeMultipartUpload(String bucket, String objectKey, String uploadId, List<String> partETags) {
        try {
            // Parse partETags string: "1:etagValue1,2:etagValue2"
            List<CompletedPart> completedParts = parseCompletedParts(partETags);

            CompletedMultipartUpload completedUpload = CompletedMultipartUpload.builder()
                    .parts(completedParts)
                    .build();

            CompleteMultipartUploadRequest request = CompleteMultipartUploadRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .uploadId(uploadId)
                    .multipartUpload(completedUpload)
                    .build();

            s3Client.completeMultipartUpload(request);
            log.info("Completed multipart upload for bucket: {}, objectKey: {}", bucket, objectKey);
        } catch (Exception e) {
            log.error("Failed to complete multipart upload for bucket: {}, objectKey: {}", bucket, objectKey, e);
            throw new RuntimeException("Failed to complete multipart upload", e);
        }
    }

    @Override
    public void abortMultipartUpload(String bucket, String objectKey, String uploadId) {
        try {
            AbortMultipartUploadRequest request = AbortMultipartUploadRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .uploadId(uploadId)
                    .build();

            s3Client.abortMultipartUpload(request);
            log.info("Aborted multipart upload for bucket: {}, objectKey: {}", bucket, objectKey);
        } catch (Exception e) {
            log.error("Failed to abort multipart upload for bucket: {}, objectKey: {}", bucket, objectKey, e);
            throw new RuntimeException("Failed to abort multipart upload", e);
        }
    }

    @Override
    public void deleteObject(String bucket, String objectKey) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .build();

            s3Client.deleteObject(request);
            log.info("Deleted object from bucket: {}, objectKey: {}", bucket, objectKey);
        } catch (Exception e) {
            log.error("Failed to delete object from bucket: {}, objectKey: {}", bucket, objectKey, e);
            throw new RuntimeException("Failed to delete object", e);
        }
    }

    @Override
    public boolean objectExists(String bucket, String objectKey) {
        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .build();

            s3Client.headObject(request);
            return true;
        } catch (NoSuchKeyException e) {
            log.debug("Object does not exist in bucket: {}, objectKey: {}", bucket, objectKey);
            return false;
        } catch (Exception e) {
            log.error("Failed to check object existence in bucket: {}, objectKey: {}", bucket, objectKey, e);
            throw new RuntimeException("Failed to check object existence", e);
        }
    }

    @Override
    public String getPresignedDownloadUrl(String bucket, String objectKey) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .build();

            GetObjectPresignRequest request = GetObjectPresignRequest.builder()
                    .signatureDuration(PRESIGN_URL_EXPIRATION_24)
                    .getObjectRequest(getObjectRequest)
                    .build();

            String url = s3Presigner.presignGetObject(request).url().toExternalForm();
            log.info("Generated presigned URL for download: {}", url);
            return url;
        } catch (Exception e) {
            log.error("Failed to generate presigned URL for part download: {}", objectKey, e);
            throw new RuntimeException("Failed to generate presigned URL", e);
        }

    }

    /**
     * 解析分片信息字符串，格式为 "1:etagValue1,2:etagValue2"
     */
    private List<CompletedPart> parseCompletedParts(List<String> partETags) {
        List<CompletedPart> completedParts = new ArrayList<>();
        if (partETags == null || partETags.isEmpty()) {
            return completedParts;
        }

        for (int i = 0; i < partETags.size(); i++) {
            completedParts.add(CompletedPart.builder()
                    .partNumber(i + 1)
                    .eTag(partETags.get(i))
                    .build());
        }
        return completedParts;
    }
}
