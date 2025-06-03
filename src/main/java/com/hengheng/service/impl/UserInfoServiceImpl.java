package com.hengheng.service.impl;

import com.hengheng.pojo.entity.UserInfoEntity;
import com.hengheng.pojo.vo.UserInfoVO;
import com.hengheng.repository.UserInfoRepository;
import com.hengheng.repository.UserRoleRepository;
import com.hengheng.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lkj
 * @Date 2025/5/28 15:45
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserInfoVO> queryUser() {
        List<UserInfoEntity> list = userInfoRepository.list();
        ArrayList<UserInfoVO> voList = new ArrayList<>();
        for (UserInfoEntity l : list) {
            UserInfoVO vo = new UserInfoVO();
            BeanUtils.copyProperties(l, vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public Boolean checkPermission(Long id, Long roleId) {
       return userRoleRepository.checkPermission(id, roleId);
    }
}
