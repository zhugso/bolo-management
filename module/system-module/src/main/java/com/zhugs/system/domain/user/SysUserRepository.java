package com.zhugs.system.domain.user;

public interface SysUserRepository {

    SysUser findById(Long userId);

    SysUser save(SysUser sysUser);

}
