package com.hengheng.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.mapper.MenuMapper;
import com.hengheng.pojo.entity.MenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/5 10:51
 * @Version 1.0
 */
@Repository
public class MenuRepository extends ServiceImpl<MenuMapper, MenuEntity> {
    public List<MenuEntity> listByPid(Long pId) {
        return this.lambdaQuery()
                .apply(pId == null || pId == 0, "pid is null")
                .eq(pId != null && pId != 0, MenuEntity::getPid, pId)
                .list();
    }

    public Long countByPid(Long pId) {
        return this.lambdaQuery().eq(MenuEntity::getMenuId, pId).count();
    }
}
