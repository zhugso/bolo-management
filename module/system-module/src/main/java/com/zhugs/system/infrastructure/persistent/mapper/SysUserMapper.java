package com.zhugs.system.infrastructure.persistent.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zhugs.system.infrastructure.persistent.entity.SysUserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUserPO> {

}
