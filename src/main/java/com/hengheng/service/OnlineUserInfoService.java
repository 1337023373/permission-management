package com.hengheng.service;

import com.hengheng.pojo.entity.UserInfoEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lkj
 * @Date 2025/6/4 11:14
 * @Version 1.0
 */
@Service
public interface OnlineUserInfoService {

    void save(String jwt, UserInfoEntity userInfo, HttpServletRequest request);
}
