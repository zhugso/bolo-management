package com.zhugs.file.domain.file;

import com.zhugs.file.domain.storage.ObjectStorage;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final ObjectStorage objectStorage;

    public FileService(ObjectStorage objectStorage) {
        this.objectStorage = objectStorage;
    }


    public String getFileUrl(File file) {
        boolean b = objectStorage.objectExists(file.getBucket(), file.getObjectKey());
        if (!b) {
            throw new RuntimeException("文件不存在！");
        }
        return objectStorage.getPresignedDownloadUrl(file.getBucket(), file.getObjectKey());
    }
}
