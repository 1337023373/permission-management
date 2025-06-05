package com.hengheng.service.impl;

import com.hengheng.pojo.entity.DeptEntity;
import com.hengheng.pojo.vo.DeptTreeVO;
import com.hengheng.repository.DeptRepository;
import com.hengheng.service.DeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public List<DeptTreeVO> getDept(Long pId) {
        List<DeptTreeVO> deptTreeVOList = new ArrayList<>();
            List<DeptEntity> list = deptRepository.listByPid(pId);
            list.forEach(deptEntity -> {
                DeptTreeVO deptTreeVO = new DeptTreeVO();
                BeanUtils.copyProperties(deptEntity, deptTreeVO);
                //查看deptId是否有等于pId的
                Long count = deptRepository.countByPid(deptEntity.getDeptId());
                deptTreeVO.setHasChildren(false);
                if (count > 0) {
                    deptTreeVO.setHasChildren(true);
                }
                deptTreeVOList.add(deptTreeVO);
            });

        return deptTreeVOList;
    }
    //public List<DeptTreeVO> getDept(Long pId) {
    //    List<DeptEntity> list = deptRepository.list();
    //    List<DeptTreeVO> deptTreeVOList = new ArrayList<>();
    //    list.forEach(l -> {
    //        DeptTreeVO deptTreeVO = new DeptTreeVO();
    //        BeanUtils.copyProperties(l, deptTreeVO);
    //        deptTreeVOList.add(deptTreeVO);
    //    });
    //    List<DeptTreeVO> tree = new ArrayList<>();
    //    for (DeptTreeVO deptEntity : deptTreeVOList) {
    //        //找到根节点
    //        if (deptEntity.getPid() == null || deptEntity.getPid() == 0) {
    //            buildTree(deptEntity, deptTreeVOList);
    //            tree.add(deptEntity);
    //        }
    //    }
    //    return tree;
    //}

    private void buildTree(DeptTreeVO deptEntity, List<DeptTreeVO> list) {
        for (DeptTreeVO entity : list) {
            if (entity.getPid() != null && entity.getPid().equals(deptEntity.getDeptId())) {
                buildTree(entity, list);
                deptEntity.getChildren().add(entity);
            }
        }

    }
}
