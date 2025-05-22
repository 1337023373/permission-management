package com.hengheng.repository;

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
}
