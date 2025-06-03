package com.hengheng.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.mapper.UserRoleMapper;
import com.hengheng.pojo.entity.UserRoleEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author lkj
 * @Date 2025/5/30 16:28
 * @Version 1.0
 */
@Repository
public class UserRoleRepository extends ServiceImpl<UserRoleMapper, UserRoleEntity> {

    public Boolean checkPermission(Long id, Long roleId) {
        UserRoleEntity entity = this.getOne(new LambdaQueryWrapper<>(UserRoleEntity.class).eq(UserRoleEntity::getUserId, id));
        if (ObjectUtils.isEmpty(entity)) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(id);
            userRoleEntity.setRoleId(roleId);
            return this.save(userRoleEntity);
        }
        return this.lambdaUpdate().eq(UserRoleEntity::getUserId, id)
                .set(UserRoleEntity::getRoleId, roleId)
                .update();
    }
}
