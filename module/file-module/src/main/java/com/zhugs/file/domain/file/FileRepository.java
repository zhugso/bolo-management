package com.zhugs.file.domain.file;

import java.util.Optional;

public interface FileRepository {

    File save(File file);

    Optional<File> findById(Long id);

    void delete(Long id);
}
