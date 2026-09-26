package com.zhugs.file.domain.storage;

import java.util.List;

/**
 * 对象存储接口（S3/MinIO/OSS/COS等兼容S3的对象存储）
 */
public interface ObjectStorage {
    /**
     * 获取预签名上传URL
     *
     * @param bucket      bucket名称
     * @param objectKey   对象key
     * @param fileSize    文件大小
     * @param contentType 内容类型
     * @return 预签名URL
     */
    String getPresignedUploadUrl(String bucket, String objectKey, Long fileSize, String contentType);

    /**
     * 启动分片上传
     *
     * @param bucket      bucket名称
     * @param objectKey   对象key
     * @param contentType 内容类型
     * @return 上传ID（uploadId）
     */
    String initiateMultipartUpload(String bucket, String objectKey, String contentType);

    /**
     * 获取分片上传的预签名URL
     *
     * @param bucket     bucket名称
     * @param objectKey  对象key
     * @param uploadId   上传ID
     * @param partNumber 分片号
     * @return 预签名URL
     */
    String getPresignedPartUploadUrl(String bucket, String objectKey, String uploadId, Integer partNumber);

    /**
     * 完成分片上传
     *
     * @param bucket    bucket名称
     * @param objectKey 对象key
     * @param uploadId  上传ID
     * @param partETags 分片ETag列表（格式："1:etagValue1,2:etagValue2"）
     */
    void completeMultipartUpload(String bucket, String objectKey, String uploadId, List<String> partETags);

    /**
     * 中止分片上传
     *
     * @param bucket    bucket名称
     * @param objectKey 对象key
     * @param uploadId  上传ID
     */
    void abortMultipartUpload(String bucket, String objectKey, String uploadId);

    /**
     * 删除对象
     *
     * @param bucket    bucket名称
     * @param objectKey 对象key
     */
    void deleteObject(String bucket, String objectKey);

    /**
     * 检查对象是否存在
     *
     * @param bucket    bucket名称
     * @param objectKey 对象key
     * @return 是否存在
     */
    boolean objectExists(String bucket, String objectKey);

    /**
     * 获取对象预下载链接
     *
     * @param bucket    bucket名称
     * @param objectKey 对象key
     * @return 预链接
     */
    String getPresignedDownloadUrl(String bucket, String objectKey);
}