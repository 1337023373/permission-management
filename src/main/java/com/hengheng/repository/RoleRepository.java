package com.hengheng.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.mapper.RoleMapper;
import com.hengheng.pojo.entity.RoleEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author lkj
 * @Date 2025/6/5 17:15
 * @Version 1.0
 */
@Repository
public class RoleRepository extends ServiceImpl<RoleMapper, RoleEntity> {
}
