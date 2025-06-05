package com.hengheng.service.impl;

import com.hengheng.repository.RoleRepository;
import com.hengheng.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lkj
 * @Date 2025/6/5 17:14
 * @Version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleRepository roleRepository;
}
