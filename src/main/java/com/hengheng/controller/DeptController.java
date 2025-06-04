package com.hengheng.controller;

import com.hengheng.common.utils.AjaxResult;
import com.hengheng.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lkj
 * @Date 2025/6/4 15:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/dept")
@Api("部门管理")
public class DeptController {
    @Resource
    private DeptService deptService;


    @ApiOperation("展示部门树")
    @GetMapping("getDept")
    public AjaxResult getDept() {
        deptService.getDept();
        return AjaxResult.success();
    }
}
