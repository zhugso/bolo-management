package com.zhugs.file.infrastructure.persistence;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileUploadTaskMapper extends BaseMapper<FileUploadTaskPo> {

}