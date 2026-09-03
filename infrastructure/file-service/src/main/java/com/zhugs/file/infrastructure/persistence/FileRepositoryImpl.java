package com.zhugs.file.infrastructure.persistence;

import com.zhugs.file.domain.file.File;
import com.zhugs.file.domain.file.FileRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private final FileObjectMapper fileObjectMapper;

    public FileRepositoryImpl(FileObjectMapper fileObjectMapper) {
        this.fileObjectMapper = fileObjectMapper;
    }


    @Override
    public File save(File file) {
        FileObjectPo po = convert2Po(file);
        if (Objects.isNull(file.getId())) {
            fileObjectMapper.insert(po);
            file.setId(po.getId());
        } else {
            fileObjectMapper.update(po);
        }
        return file;
    }

    @Override
    public Optional<File> findById(Long id) {
        FileObjectPo fileObjectPo = fileObjectMapper.selectOneById(id);
        if (Objects.isNull(fileObjectPo)) {
            return Optional.empty();
        }
        return Optional.of(convert2Do(fileObjectPo));
    }

    @Override
    public void delete(Long id) {
        fileObjectMapper.deleteById(id);
    }

    private FileObjectPo convert2Po(File file) {
        FileObjectPo fileObjectPo = new FileObjectPo();
        fileObjectPo.setId(file.getId());
        fileObjectPo.setFileName(file.getFileName());
        fileObjectPo.setFileSize(file.getFileSize());
        fileObjectPo.setContentType(file.getContentType());
        fileObjectPo.setFileExt(file.getFileExt());
        fileObjectPo.setFileType(file.getFileType());
        fileObjectPo.setBucket(file.getBucket());
        fileObjectPo.setObjectKey(file.getObjectKey());
        fileObjectPo.setStorageType(file.getStorageType());
        fileObjectPo.setQuickHash(file.getQuickHash());
        fileObjectPo.setSha256(file.getSha256());
        fileObjectPo.setHashStatus(file.getHashStatus());
        fileObjectPo.setStatus(file.getStatus());
        fileObjectPo.setCreatorId(file.getCreatorId());
        fileObjectPo.setDeleted(file.getDeleted());
        return fileObjectPo;
    }

    private File convert2Do(FileObjectPo fileObjectPo) {
        return File.builder()
                .id(fileObjectPo.getId())
                .fileName(fileObjectPo.getFileName())
                .fileSize(fileObjectPo.getFileSize())
                .contentType(fileObjectPo.getContentType())
                .fileExt(fileObjectPo.getFileExt())
                .fileType(fileObjectPo.getFileType())
                .bucket(fileObjectPo.getBucket())
                .objectKey(fileObjectPo.getObjectKey())
                .storageType(fileObjectPo.getStorageType())
                .quickHash(fileObjectPo.getQuickHash())
                .sha256(fileObjectPo.getSha256())
                .hashStatus(fileObjectPo.getHashStatus())
                .status(fileObjectPo.getStatus())
                .creatorId(fileObjectPo.getCreatorId())
                .deleted(fileObjectPo.getDeleted())
                .build();
    }
}
