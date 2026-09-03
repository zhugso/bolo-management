package com.zhugs.file.application;

import com.zhugs.file.domain.upload.UploadService;
import com.zhugs.file.domain.upload.UploadTask;
import com.zhugs.file.interfaces.dto.UploadRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadApplicationService {

    private final UploadService uploadService;

    public UploadApplicationService(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    public Map<String, Object> createUploadTask(UploadRequest uploadRequest) {
        Map<String, Object> response = new HashMap<>();
        UploadTask uploadTask = uploadService.createUploadTask(
                uploadRequest.fileName(),
                uploadRequest.fileSize(),
                uploadRequest.contentType(),
                uploadRequest.fileHash());

        response.put("fileId", uploadTask.getFileId());
        response.put("uploadType", uploadTask.getUploadType());
        response.put("uploadTaskId", uploadTask.getId());
        response.put("uploadId", uploadTask.getUploadId());
        response.put("partSize", uploadTask.getPartSize());
        response.put("partCount", uploadTask.getPartCount());
        return response;
    }

    public Map<String, Object> getPresign(Long uploadTaskId, List<Integer> partNums) {
        Map<String, Object> presign = uploadService.getPresign(uploadTaskId, partNums);
        return presign;
    }

    public Map<String, Object> getUploadInfo(Long uploadTaskId) {
        UploadTask uploadInfo = uploadService.getUploadInfo(uploadTaskId);
        Map<String, Object> resp = new HashMap<>();

        resp.put("uploadTaskId",uploadInfo.getId());
        resp.put("fileId",uploadInfo.getFileId());
        resp.put("uploadType",uploadInfo.getUploadType());
        resp.put("status",uploadInfo.getStatus());
        resp.put("fileSize",uploadInfo.getFileSize());
        resp.put("expireTime",uploadInfo.getExpireTime());
        return resp;
    }

    public void completeUpload(Long uploadTaskId, List<String> partETags) {
        uploadService.completeUpload(uploadTaskId, partETags);
    }

    public void abortUpload(Long uploadTaskId) {
        uploadService.abortUpload(uploadTaskId);
    }



}
