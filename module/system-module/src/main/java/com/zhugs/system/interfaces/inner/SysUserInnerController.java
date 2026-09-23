package com.zhugs.system.interfaces.inner;

import com.zhugs.system.application.query.SysUserQueryService;
import com.zhugs.system.infrastructure.persistent.entity.SysUserPO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("inner/system/user")
@RestController
public class SysUserInnerController {

    private final SysUserQueryService sysUserQueryService;

    public SysUserInnerController(SysUserQueryService sysUserQueryService) {
        this.sysUserQueryService = sysUserQueryService;
    }

    @GetMapping
    public Map<String, Object> getUserPasswordByUsername(String username) {
        SysUserPO user = sysUserQueryService.getUser(username);
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("nickname", user.getNickname());
        map.put("status", user.getStatus());
        return  map;
    }


}
