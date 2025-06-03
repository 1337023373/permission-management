package com.hengheng.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author lkj
 * @Date 2025/5/30 16:26
 * @Version 1.0
 */
@Data
@TableName("sys_users_roles")
public class UserRoleEntity {
    private Long userId;
    private Long roleId;
}
