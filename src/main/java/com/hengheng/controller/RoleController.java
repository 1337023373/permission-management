package com.hengheng.controller;

import com.hengheng.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lkj
 * @Date 2025/6/4 15:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/menu")
@Api("角色管理")
public class RoleController {
    @Resource
    private RoleService roleService;


}
