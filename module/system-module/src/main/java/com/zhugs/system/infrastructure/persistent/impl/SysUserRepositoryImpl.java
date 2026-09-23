package com.zhugs.system.infrastructure.persistent.impl;

import com.zhugs.system.domain.user.SysUser;
import com.zhugs.system.domain.user.SysUserRepository;
import com.zhugs.system.infrastructure.persistent.mapper.SysUserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserRepositoryImpl implements SysUserRepository {

    private final SysUserMapper sysUserMapper;

    public SysUserRepositoryImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public SysUser findById(Long userId) {
        return null;
    }

    @Override
    public SysUser save(SysUser sysUser) {
        return null;
    }
}
