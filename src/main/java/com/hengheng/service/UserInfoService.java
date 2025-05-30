package com.hengheng.service;

import com.hengheng.pojo.vo.UserInfoVO;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/5/28 15:42
 * @Version 1.0
 */
public interface UserInfoService {
    List<UserInfoVO> queryUser();
}
