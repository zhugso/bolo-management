package com.zhugs.file.domain.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadTask {
    /**
     * 上传任务ID
     */
    private Long id;

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 上传方式：SIMPLE/MULTIPART
     */
    private String uploadType;

    /**
     * S3 Multipart UploadId，普通上传为空
     */
    private String uploadId;

    /**
     * S3 Bucket
     */
    private String bucket;

    /**
     * S3 Object Key
     */
    private String objectKey;

    /**
     * 文件大小，单位byte
     */
    private Long fileSize;

    /**
     * 分片大小，普通上传为空
     */
    private Long partSize;

    /**
     * 分片数量，普通上传为空
     */
    private Integer partCount;

    /**
     * 状态 0上传中，1上传成功，2中止上传
     */
    private Byte status;

    /**
     * 上传任务过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 创建人
     */
    private Long creatorId;

    public void markCompleted() {
        this.status = (byte) 1;
    }

    public void markAborted() {
        this.status = (byte) 2;
    }
}
