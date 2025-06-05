package com.hengheng.controller;

import com.hengheng.common.utils.AjaxResult;
import com.hengheng.pojo.vo.MenuTreeVO;
import com.hengheng.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/4 15:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/menu")
@Api("菜单管理")
public class MenuController {
    @Resource
    private MenuService menuService;


    @ApiOperation("展示部门树")
    @GetMapping("getMenu")
    public AjaxResult getMenu(@RequestParam(required = false) Long pId) {
        List<MenuTreeVO> dept = menuService.getMenu(pId);
        return AjaxResult.success(dept);
    }
}
