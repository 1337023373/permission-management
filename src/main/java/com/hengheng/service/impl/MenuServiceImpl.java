package com.hengheng.service.impl;

import com.hengheng.pojo.entity.MenuEntity;
import com.hengheng.pojo.vo.MenuTreeVO;
import com.hengheng.repository.MenuRepository;
import com.hengheng.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/5 10:50
 * @Version 1.0
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuRepository menuRepository;

    @Override
    public List<MenuTreeVO> getMenu(Long pId) {
        List<MenuEntity> menuEntities = menuRepository.listByPid(pId);
        List<MenuTreeVO> menuTreeVOS = new ArrayList<>();
        for (MenuEntity menuEntity : menuEntities) {
            MenuTreeVO menuTreeVO = new MenuTreeVO();
            BeanUtils.copyProperties( menuEntity, menuTreeVO);
        //   判断是否可以展开
            Long count = menuRepository.countByPid(pId);
            if (count > 0) {
                menuTreeVO.setSubCount(count.intValue());
            }
            menuTreeVOS.add(menuTreeVO);
        }
        return menuTreeVOS;
    }
}
