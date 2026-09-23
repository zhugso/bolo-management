package com.zhugs.system.application.query;

import com.zhugs.system.infrastructure.persistent.entity.SysUserPO;

public interface SysUserQueryRepository {

    SysUserPO getUser(String username);

}
