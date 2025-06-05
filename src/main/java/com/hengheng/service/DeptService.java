package com.hengheng.service;

import com.hengheng.pojo.vo.DeptTreeVO;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/4 15:08
 * @Version 1.0
 */
public interface DeptService {

    List<DeptTreeVO> getDept(Long pId);
}
