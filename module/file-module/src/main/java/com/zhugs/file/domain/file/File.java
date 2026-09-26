package com.zhugs.file.domain.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    /**
     * 文件ID
     */
    private Long id;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 文件大小，单位byte
     */
    private Long fileSize;

    /**
     * MIME类型
     */
    private String contentType;

    /**
     * 文件扩展名
     */
    private String fileExt;

    /**
     * 文件类型：IMAGE/VIDEO/AUDIO/DOCUMENT/OTHER
     */
    private String fileType;

    /**
     * S3 Bucket
     */
    private String bucket;

    /**
     * S3 Object Key
     */
    private String objectKey;

    /**
     * 存储类型：S3/MINIO/OSS
     */
    private String storageType;

    /**
     * quick_hash
     */
    private String quickHash;

    /**
     * SHA256
     */
    private String sha256;

    /**
     * Hash状态：0未计算 1计算中 2完成 3失败
     */
    private Byte hashStatus;

    /**
     * 文件状态：0上传中 1可用 2处理中 3失败 4删除
     */
    private Byte status;

    /**
     * 创建人
     */
    private Long creatorId;

    private Byte deleted;

    public void markAvailable() {
        this.status = (byte) 1;
    }

    public void markDeleted() {
        this.status = (byte) 4;
        this.deleted = (byte) 1;
    }
}
