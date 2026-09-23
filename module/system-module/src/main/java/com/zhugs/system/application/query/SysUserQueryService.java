package com.zhugs.system.application.query;

import com.zhugs.system.infrastructure.persistent.entity.SysUserPO;
import org.springframework.stereotype.Service;

@Service
public class SysUserQueryService {

    private final SysUserQueryRepository sysUserQueryRepository;

    public SysUserQueryService(SysUserQueryRepository sysUserQueryRepository) {
        this.sysUserQueryRepository = sysUserQueryRepository;
    }

    public SysUserPO getUser(String username) {
        return sysUserQueryRepository.getUser(username);
    }
}
