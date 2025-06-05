package com.hengheng.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.mapper.DeptMapper;
import com.hengheng.pojo.entity.DeptEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/4 15:12
 * @Version 1.0
 */
@Repository
public class DeptRepository extends ServiceImpl<DeptMapper, DeptEntity>  {

    public List<DeptEntity> listByPid(Long pId) {
        return this.lambdaQuery()
                .apply(pId == null || pId == 0, "pId is null")
                .eq(pId != null && pId != 0,DeptEntity::getPid,pId)
                .list();
    }

    public Long countByPid(Long deptId) {
        return this.lambdaQuery().eq(DeptEntity::getPid, deptId).count();
    }
}
