package com.zhugs.system.infrastructure.persistent.impl;

import com.zhugs.system.application.query.SysUserQueryRepository;
import com.zhugs.system.infrastructure.persistent.entity.SysUserPO;
import com.zhugs.system.infrastructure.persistent.mapper.SysUserMapper;
import org.springframework.stereotype.Repository;

import static com.zhugs.system.infrastructure.persistent.entity.table.SysUserPOTableDef.SYS_USER_PO;

@Repository
public class SysUserQueryRepositoryImpl implements SysUserQueryRepository {


    private final SysUserMapper sysUserMapper;

    public SysUserQueryRepositoryImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }


    @Override
    public SysUserPO getUser(String username) {
        SysUserPO sysUserPO = sysUserMapper.selectOneByCondition(SYS_USER_PO.USERNAME.eq(username));
        return sysUserPO;
    }
}
