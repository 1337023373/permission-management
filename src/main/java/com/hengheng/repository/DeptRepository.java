package com.hengheng.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.mapper.DeptMapper;
import com.hengheng.pojo.entity.DeptEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author lkj
 * @Date 2025/6/4 15:12
 * @Version 1.0
 */
@Repository
public class DeptRepository extends ServiceImpl<DeptMapper, DeptEntity>  {
}
