//package com.zhugs.system.infrastructure.persistent.entity;
//
//import com.mybatisflex.annotation.Table;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
/**
 * 系统用户信息表
 */
//@Data
//@Table(value = "sys_user")
//public class SysUserPOBAK {
//    /**
//     * 用户ID
//     */
//    private Long id;
//
//    /**
//     * 登录账号
//     */
//    private String username;
//
//    /**
//     * 登录密码
//     */
//    private String password;
//
//    /**
//     * 用户昵称
//     */
//    private String nickname;
//
//    /**
//     * 用户邮箱
//     */
//    private String email;
//
//    /**
//     * 手机号码
//     */
//    private String phone;
//
//    /**
//     * 头像地址
//     */
//    private String avatar;
//
//    /**
//     * 性别(0未知,1男,2女)
//     */
//    private String gender;
//
//    /**
//     * 部门ID
//     */
//    private Long deptId;
//
//    /**
//     * 状态(0启用,1禁用)
//     */
//    private String status;
//
//    /**
//     * 最后登录IP
//     */
//    private String lastLoginIp;
//
//    /**
//     * 上次登录时间
//     */
//    private LocalDateTime lastLoginTime;
//
//    /**
//     * 创建者
//     */
//    private Long createBy;
//
//    /**
//     * 创建时间
//     */
//    private LocalDateTime createTime;
//
//    /**
//     * 更新者
//     */
//    private Long updateBy;
//
//    /**
//     * 更新时间
//     */
//    private LocalDateTime updateTime;
//
//    /**
//     * 删除标志（0代表存在 1代表删除）
//     */
//    private String deleted;
//}