package com.zhugs.file.domain.upload;

import java.util.Optional;

public interface UploadRepository {

    UploadTask save(UploadTask uploadTask);

    Optional<UploadTask> findById(Long id);


}
