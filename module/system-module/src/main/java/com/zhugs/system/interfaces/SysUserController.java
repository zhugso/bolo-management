package com.zhugs.system.interfaces;

import com.zhugs.system.application.query.SysUserQueryService;
import com.zhugs.system.infrastructure.persistent.entity.SysUserPO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("system/user")
@RestController
public class SysUserController {

    private final SysUserQueryService sysUserQueryService;

    public SysUserController(SysUserQueryService sysUserQueryService) {
        this.sysUserQueryService = sysUserQueryService;
    }

    @GetMapping
    public ResponseEntity<SysUserPO> getUser(String username) {
        SysUserPO user = sysUserQueryService.getUser(username);
        return ResponseEntity.ok(user);
    }

}
