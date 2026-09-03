package com.zhugs.file.domain.upload;

import com.zhugs.file.domain.file.File;
import com.zhugs.file.domain.file.FileRepository;
import com.zhugs.file.domain.storage.ObjectStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UploadService {

    private final ObjectStorage objectStorage;

    private final FileRepository fileRepository;

    private final UploadRepository uploadRepository;

    @Value("${file.storage.s3.bucket-name}")
    private String bucket;

    @Value("${file.storage.s3.prefix}")
    private String prefix;

    @Value("${file.upload.max-size:10737418240}") // 10GB
    private Long maxFileSize;

    @Value("${file.upload.part-size:5242880}") // 5MB
    private Long partSize;

    @Value("${file.upload.expire-hours:24}")
    private Long uploadExpireHours;

    public UploadService(ObjectStorage objectStorage,
                         FileRepository fileRepository,
                         UploadRepository uploadRepository) {
        this.objectStorage = objectStorage;
        this.fileRepository = fileRepository;
        this.uploadRepository = uploadRepository;
    }

    public UploadTask createUploadTask(String fileName, Long fileSize, String contentType, String fileHash) {
        File file = createFileForUpload(fileName, fileSize, contentType, bucket, fileHash);
        file = fileRepository.save(file);

        String uploadType = uploadType(fileSize);
        String objectKey = file.getObjectKey();
        UploadTask uploadTask = UploadTask.builder()
                .fileId(file.getId())
                .uploadType(uploadType)
                .bucket(bucket)
                .objectKey(objectKey)
                .fileSize(fileSize)
                .status((byte) 0)
                .expireTime(LocalDateTime.now().plusHours(uploadExpireHours))
                .build();
        if (uploadType.equals(UploadConstant.MULTIPART)) {
            String uploadId = objectStorage.initiateMultipartUpload(bucket, objectKey, contentType);

            uploadTask.setUploadId(uploadId);
            uploadTask.setPartSize(partSize);
            uploadTask.setPartCount((int) Math.ceil((double) fileSize / partSize));
        }
        uploadTask = uploadRepository.save(uploadTask);
        return uploadTask;
    }

    public Map<String, Object> getPresign(Long uploadTaskId, List<Integer> partNumbers) {
        Optional<UploadTask> uploadTaskOptional = uploadRepository.findById(uploadTaskId);
        if (uploadTaskOptional.isEmpty()) {
            throw new RuntimeException("上传任务不存在！");
        }
        UploadTask uploadTask = uploadTaskOptional.get();
        Optional<File> fileOptional = fileRepository.findById(uploadTask.getFileId());
        File file = fileOptional.get();
        Map<String, Object> preUrlMap = new HashMap<>();
        if (UploadConstant.SIMPLE.equals(uploadTask.getUploadType())) {
            String presignUrl = objectStorage.getPresignedUploadUrl(bucket,
                    uploadTask.getObjectKey(),
                    uploadTask.getFileSize(),
                    file.getContentType());
            preUrlMap.put("0", presignUrl);
        } else {
            partNumbers.forEach(partNumber -> {
                String presignUrl = objectStorage.getPresignedPartUploadUrl(bucket,
                        uploadTask.getObjectKey(),
                        uploadTask.getUploadId(),
                        partNumber
                );
                preUrlMap.put(partNumber.toString(), presignUrl);
            });
        }
        return preUrlMap;
    }

    public void abortUpload(Long uploadTaskId) {
        Optional<UploadTask> uploadTaskOptional = uploadRepository.findById(uploadTaskId);
        if (uploadTaskOptional.isEmpty()) {
            throw new RuntimeException("不存在的上传任务");
        }

        UploadTask uploadTask = uploadTaskOptional.get();
        Optional<File> fileOptional = fileRepository.findById(uploadTask.getFileId());
        File file = fileOptional.get();
        abortUpload(file, uploadTask);
        objectStorage.abortMultipartUpload(bucket, uploadTask.getObjectKey(), uploadTask.getUploadId());
        uploadRepository.save(uploadTask);
        fileRepository.save(file);
    }

    public UploadTask getUploadInfo(Long uploadTaskId) {
        Optional<UploadTask> uploadTaskOptional = uploadRepository.findById(uploadTaskId);
        if (uploadTaskOptional.isEmpty()) {
            throw new RuntimeException("不存在的上传任务");
        }
        return uploadTaskOptional.get();
    }

    public void completeUpload(Long uploadTaskId, List<String> partETags) {
        Optional<UploadTask> uploadTaskOptional = uploadRepository.findById(uploadTaskId);
        if (uploadTaskOptional.isEmpty()) {
            throw new RuntimeException("不存在的上传任务");
        }
        UploadTask uploadTask = uploadTaskOptional.get();
        Optional<File> fileOptional = fileRepository.findById(uploadTask.getFileId());
        File file = fileOptional.get();
        completeUpload(file, uploadTask);

        objectStorage.completeMultipartUpload(uploadTask.getBucket(),
                uploadTask.getObjectKey(),
                uploadTask.getUploadId(),
                partETags
        );
    }


    private String generateObjectKey(String prefix, String contentType) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString();
        return prefix + "/" + contentType + "/" + timestamp + "-" + uuid;
    }

    private String getFileExtension(String fileName) {
        if (Objects.isNull(fileName) || fileName.isEmpty()) {
            return "";
        }
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0 && lastDot < fileName.length() - 1) {
            return fileName.substring(lastDot + 1).toLowerCase();
        }
        return "";
    }

    private File createFileForUpload(String fileName, Long fileSize, String contentType, String bucket, String quickHash) {
        return File.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .contentType(contentType)
                .fileExt(getFileExtension(fileName))
                .fileType("")
                .bucket(bucket)
                .objectKey(generateObjectKey(prefix, contentType))
                .storageType("S3")
                .quickHash(quickHash)
                .hashStatus((byte) 0)
                .status((byte) 0)
                .deleted((byte) 0)
                .build();
    }

    private String uploadType(Long fileSize) {
        if (partSize.compareTo(fileSize) > 0) {
            return UploadConstant.SIMPLE;
        } else {
            return UploadConstant.MULTIPART;
        }
    }

    private void completeUpload(File file, UploadTask uploadTask) {
        file.markAvailable();
        uploadTask.markCompleted();
    }

    public void abortUpload(File file, UploadTask uploadTask) {
        file.markDeleted();
        uploadTask.markAborted();
    }


}
