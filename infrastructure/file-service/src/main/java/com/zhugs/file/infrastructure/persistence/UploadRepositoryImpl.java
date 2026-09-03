package com.zhugs.file.infrastructure.persistence;

import com.zhugs.file.domain.upload.UploadRepository;
import com.zhugs.file.domain.upload.UploadTask;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class UploadRepositoryImpl implements UploadRepository {

    private final FileUploadTaskMapper fileUploadTaskMapper;

    public UploadRepositoryImpl(FileUploadTaskMapper fileUploadTaskMapper) {
        this.fileUploadTaskMapper = fileUploadTaskMapper;
    }


    @Override
    public UploadTask save(UploadTask uploadTask) {
        FileUploadTaskPo po = convert2Po(uploadTask);
        if (Objects.isNull(uploadTask.getId())) {
            fileUploadTaskMapper.insert(po);
            uploadTask.setId(po.getId());
        } else {
            fileUploadTaskMapper.update(po);
        }
        return uploadTask;
    }

    @Override
    public Optional<UploadTask> findById(Long id) {
        FileUploadTaskPo fileUploadTaskPo = fileUploadTaskMapper.selectOneById(id);
        if (Objects.isNull(fileUploadTaskPo)) {
            return Optional.empty();
        }
        return Optional.of(convert2Do(fileUploadTaskPo));
    }

    private FileUploadTaskPo convert2Po(UploadTask uploadTask) {
        FileUploadTaskPo po = new FileUploadTaskPo();
        po.setId(uploadTask.getId());
        po.setFileId(uploadTask.getFileId());
        po.setUploadType(uploadTask.getUploadType());
        po.setUploadId(uploadTask.getUploadId());
        po.setBucket(uploadTask.getBucket());
        po.setObjectKey(uploadTask.getObjectKey());
        po.setFileSize(uploadTask.getFileSize());
        po.setPartSize(uploadTask.getPartSize());
        po.setPartCount(uploadTask.getPartCount());
        po.setStatus(uploadTask.getStatus());
        po.setExpireTime(uploadTask.getExpireTime());
        po.setCreatorId(uploadTask.getCreatorId());
        return po;
    }

    private UploadTask convert2Do(FileUploadTaskPo entity) {
        return UploadTask.builder()
                .id(entity.getId())
                .fileId(entity.getFileId())
                .uploadType(entity.getUploadType())
                .uploadId(entity.getUploadId())
                .bucket(entity.getBucket())
                .objectKey(entity.getObjectKey())
                .fileSize(entity.getFileSize())
                .partSize(entity.getPartSize())
                .partCount(entity.getPartCount())
                .status(entity.getStatus())
                .expireTime(entity.getExpireTime())
                .creatorId(entity.getCreatorId())
                .build();
    }
}
