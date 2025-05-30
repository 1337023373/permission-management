package com.hengheng.controller;

import com.hengheng.common.utils.AjaxResult;
import com.hengheng.pojo.vo.UserInfoVO;
import com.hengheng.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lkj
 * @Date 2025/5/28 15:31
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/users")
@Api(tags = "用户管理")
public class UserController {

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("查询所有用户信息")
    @GetMapping
    @PreAuthorize("@el.check()")
    //@AnonymousAccess
    public AjaxResult queryUser() {
        List<UserInfoVO> userInfoVOS = userInfoService.queryUser();
        return AjaxResult.success(userInfoVOS);
    }
}
