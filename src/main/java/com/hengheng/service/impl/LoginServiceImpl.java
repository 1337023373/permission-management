package com.hengheng.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.hengheng.pojo.entity.UserInfoEntity;
import com.hengheng.pojo.query.RegisterQuery;
import com.hengheng.repository.UserInfoRepository;
import com.hengheng.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lkj
 * @Date 2025/5/22 14:47
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserInfoRepository userInfoRepository;

    /**
     * @param registerQuery
     * @return boolean
     * @description 注册账号
     * @author lkj
     * @date 2025/5/22
     */
    @Override
    public Boolean register(RegisterQuery registerQuery) {
        String encryptedPwd  = SecureUtil.md5(registerQuery.getPassword());
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        BeanUtils.copyProperties(registerQuery, userInfoEntity);
        userInfoEntity.setPassword(encryptedPwd);
        return userInfoRepository.saveRegister(userInfoEntity);
    }
}
