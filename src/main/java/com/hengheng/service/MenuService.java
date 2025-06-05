package com.hengheng.service;

import com.hengheng.pojo.vo.MenuTreeVO;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/5 10:39
 * @Version 1.0
 */
public interface MenuService {
    List<MenuTreeVO> getMenu(Long pId);
}
