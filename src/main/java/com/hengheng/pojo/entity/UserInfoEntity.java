package com.hengheng.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hengheng.pojo.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author lkj
 * @Date 2025/5/22 14:52
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class UserInfoEntity extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("部门ID")
    private Long deptId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像地址")
    private String avatarName;

    @ApiModelProperty("头像真实路径")
    private String avatarPath;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("是否为管理员账号")
    private Boolean isAdmin = false;

    @ApiModelProperty("是否启用")
    private Boolean enabled = true;

    @ApiModelProperty("密码重置时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime pwdResetTime;
}
