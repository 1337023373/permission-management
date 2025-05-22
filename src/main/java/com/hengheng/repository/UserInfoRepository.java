package com.hengheng.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.mapper.UserInfoMapper;
import com.hengheng.pojo.entity.UserInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author lkj
 * @Date 2025/5/22 14:48
 * @Version 1.0
 */
@Repository
public class UserInfoRepository extends ServiceImpl<UserInfoMapper, UserInfoEntity> {

    public Boolean saveRegister(UserInfoEntity userInfoEntity) {
        return this.save(userInfoEntity);
    }

    /**
     * @param
     * @return
     * @description 根据用户名查询用户信息
     * @author lkj
     * @date 2025/5/22
     */

    public UserInfoEntity findUserByUserName(String userName) {
       return this.getOne(new LambdaQueryWrapper<>(UserInfoEntity.class).eq(UserInfoEntity::getUsername, userName));
    }
}
