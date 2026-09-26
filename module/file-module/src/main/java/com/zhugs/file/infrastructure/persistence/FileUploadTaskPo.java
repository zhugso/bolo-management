package com.zhugs.file.infrastructure.persistence;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table("file_upload_task")
public class FileUploadTaskPo {
    /**
    * 上传任务ID
    */
    @Id(keyType = KeyType.Auto)
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
    * 状态
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

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

}