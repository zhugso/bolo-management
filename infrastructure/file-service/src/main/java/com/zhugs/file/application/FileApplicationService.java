package com.zhugs.file.application;

import com.zhugs.file.domain.file.File;
import com.zhugs.file.domain.file.FileRepository;
import com.zhugs.file.domain.file.FileService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FileApplicationService {

    private final FileService fileService;

    private final FileRepository fileRepository;

    public FileApplicationService(FileService fileService, FileRepository fileRepository) {
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    public Map<String, Object> getFile(Long fileId) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isEmpty()) {
            throw new RuntimeException("文件不存在！");
        }
        Map<String, Object> response = new HashMap<>();
        File file = fileOptional.get();

        response.put("fileId", file.getId());
        response.put("fileName", file.getFileName());
        response.put("fileType", file.getFileType());
        response.put("fileSize", file.getFileSize());
        return response;
    }

    public String getFileUrl(Long fileId){
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isEmpty()) {
            throw new RuntimeException("文件不存在！");
        }
        return fileService.getFileUrl(fileOptional.get());
    }

    public void deleteFile(Long fileId) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isEmpty()) {
            throw new RuntimeException("文件不存在！");
        }
        File file = fileOptional.get();
        file.markDeleted();

        fileRepository.save(file);
    }

}
