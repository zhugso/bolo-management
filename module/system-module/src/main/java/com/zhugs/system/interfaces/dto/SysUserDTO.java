package com.zhugs.system.interfaces.dto;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户信息表
 */
@Data
public class SysUserDTO {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 状态(启用,禁用)
     */
    private String status;

}