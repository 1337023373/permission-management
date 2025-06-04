package com.hengheng.service.impl;

import com.hengheng.pojo.entity.DeptEntity;
import com.hengheng.repository.DeptRepository;
import com.hengheng.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/4 15:10
 * @Version 1.0
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptRepository deptRepository;

    @Override
    public void getDept() {
        List<DeptEntity> list = deptRepository.list();
        for (DeptEntity deptEntity : list) {

        }
    }
}
