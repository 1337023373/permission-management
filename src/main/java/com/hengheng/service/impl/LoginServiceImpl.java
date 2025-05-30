package com.hengheng.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.hengheng.common.config.JwtProperties;
import com.hengheng.common.utils.AjaxResult;
import com.hengheng.common.utils.RedisCache;
import com.hengheng.common.utils.TokenUtil;
import com.hengheng.pojo.entity.UserInfoEntity;
import com.hengheng.pojo.query.LoginQuery;
import com.hengheng.pojo.query.RegisterQuery;
import com.hengheng.repository.UserInfoRepository;
import com.hengheng.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lkj
 * @Date 2025/5/22 14:47
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private RedisCache redisCache;
    @Resource
    private JwtProperties jwtProperties;

    /**
     * @param registerQuery
     * @return boolean
     * @description 注册账号
     * @author lkj
     * @date 2025/5/22
     */
    @Override
    public Boolean register(RegisterQuery registerQuery) {
        String encryptedPwd = SecureUtil.md5(registerQuery.getPassword());
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        BeanUtils.copyProperties(registerQuery, userInfoEntity);
        userInfoEntity.setPassword(encryptedPwd);
        return userInfoRepository.saveRegister(userInfoEntity);
    }

    /**
     * @param loginQuery
     * @return
     * @description 登录
     * @author lkj
     * @date 2025/5/22
     */
    @Override
    public AjaxResult login(LoginQuery loginQuery) {
        String key = "captcha:" + loginQuery.getCaptchaUUID();
        System.out.println(key);
        String cachedCaptcha = redisCache.getCacheObject(key).toString();

        if (cachedCaptcha == null) {
            return AjaxResult.error("AjaxResult");
        }
        if (!cachedCaptcha.equalsIgnoreCase(loginQuery.getCode())) {
            return AjaxResult.error("验证码错误");
        }
        //验证成功删除缓存，避免重复使用
        redisCache.deleteObject(key);

        //验证账号密码
        UserInfoEntity userInfo = userInfoRepository.findUserByUserName(loginQuery.getUserName());
        if (ObjectUtil.isEmpty(userInfo)) {
            return AjaxResult.error("用户不存在");
        }
        if (!userInfo.getPassword().equals(SecureUtil.md5(loginQuery.getPassword()))) {
            return AjaxResult.error("密码不正确");
        }

        //生成token
        String jwt = TokenUtil.createJWT(userInfo);
        Map<String, Object> data = new HashMap<>();
        data.put("token", jwtProperties.getTokenStartWith() + jwt);
        return AjaxResult.success(data);
    }
}
