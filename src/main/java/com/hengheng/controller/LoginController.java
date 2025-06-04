package com.hengheng.controller;

import com.hengheng.common.annotation.rest.AnonymousPostMapping;
import com.hengheng.common.utils.AjaxResult;
import com.hengheng.pojo.query.LoginQuery;
import com.hengheng.pojo.query.RegisterQuery;
import com.hengheng.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

;

/**
 * @Author lkj
 * @Date 2025/5/22 13:14
 * @Version 1.0
 */

@RestController
@RequestMapping("/login")
@Api(tags = "登录模块")
public class LoginController {
    @Resource
    private LoginService loginService;

    @AnonymousPostMapping("register")
    @ApiOperation("注册账号")
    public AjaxResult register(@RequestBody @Valid RegisterQuery registerQuery) {
        Boolean result = loginService.register(registerQuery);
        if (result) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    @AnonymousPostMapping("login")
    @ApiOperation("登录")
    public AjaxResult login(@RequestBody @Valid LoginQuery loginQuery, HttpServletRequest httpServletRequest) {
        return loginService.login(loginQuery, httpServletRequest);
    }


    @AnonymousPostMapping("loginOut")
    @ApiOperation("登出")
    public AjaxResult loginOut(HttpServletRequest httpRequest) {
        loginService.loginOut(httpRequest);
        return AjaxResult.success();
    }
}
