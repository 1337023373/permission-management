package com.hengheng.service;

import com.hengheng.common.utils.AjaxResult;
import com.hengheng.pojo.query.LoginQuery;
import com.hengheng.pojo.query.RegisterQuery;

/**
 * @Author lkj
 * @Date 2025/5/22 14:21
 * @Version 1.0
 */

public interface LoginService {

    /**
     * @description 注册账号
     * @param
     * @author  lkj
     * @date  2025/5/22
     * @return  boolean
     */
    Boolean register(RegisterQuery registerQuery);

    /**
     * @description 登录
     * @param
     * @author  lkj
     * @date  2025/5/22
     * @return
     */
    AjaxResult login(LoginQuery loginQuery);
}
