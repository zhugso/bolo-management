package com.zhugs.file.interfaces.dto;

public record UploadRequest(
        String fileName,
        Long fileSize,
        String contentType,
        String fileHash
) {
}
