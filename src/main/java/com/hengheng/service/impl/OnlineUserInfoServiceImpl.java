package com.hengheng.service.impl;


import com.hengheng.common.utils.EncryptUtils;
import com.hengheng.common.utils.RedisUtils;
import com.hengheng.common.utils.StringUtils;
import com.hengheng.common.utils.TokenUtil;
import com.hengheng.pojo.dto.OnlineUserDto;
import com.hengheng.pojo.entity.UserInfoEntity;
import com.hengheng.security.config.bean.SecurityProperties;
import com.hengheng.service.OnlineUserInfoService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author lkj
 * @Date 2025/6/4 11:15
 * @Version 1.0
 */
@Slf4j
public class OnlineUserInfoServiceImpl implements OnlineUserInfoService {
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void save(String jwt, UserInfoEntity userInfo, HttpServletRequest request) {
        String ip = StringUtils.getIp(request);
        String browser = StringUtils.getBrowser(request);
        String address = StringUtils.getCityInfo(ip);
        OnlineUserDto onlineUserDto = null;
        try {
            onlineUserDto = new OnlineUserDto(userInfo.getUsername(), userInfo.getNickName(), null, browser, ip, address, EncryptUtils.desEncrypt(jwt), new Date());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        //生成redis-key
        String loginKey = tokenUtil.loginKey(jwt);
        redisUtils.set(loginKey, onlineUserDto, securityProperties.getTokenValidityInSeconds(), TimeUnit.MILLISECONDS);

    }
}
