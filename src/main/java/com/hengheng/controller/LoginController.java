package com.hengheng.controller;

import com.hengheng.common.utils.AjaxResult;
import com.hengheng.pojo.query.LoginQuery;
import com.hengheng.pojo.query.RegisterQuery;
import com.hengheng.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @PostMapping("register")
    @ApiOperation("注册账号")
    public AjaxResult register(@RequestBody @Valid RegisterQuery registerQuery) {
        Boolean result = loginService.register(registerQuery);
        if (result) {
            return AjaxResult.success();
        }else {
            return AjaxResult.error();
        }
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public AjaxResult login(@RequestBody @Valid LoginQuery loginQuery) {
        return loginService.login(loginQuery);
    }
}
