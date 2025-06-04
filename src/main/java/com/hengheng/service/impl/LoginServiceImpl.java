package com.hengheng.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hengheng.common.utils.AjaxResult;
import com.hengheng.common.utils.RedisCache;
import com.hengheng.common.utils.TokenUtil;
import com.hengheng.pojo.dto.LoginUser;
import com.hengheng.pojo.entity.UserInfoEntity;
import com.hengheng.pojo.query.LoginQuery;
import com.hengheng.pojo.query.RegisterQuery;
import com.hengheng.repository.UserInfoRepository;
import com.hengheng.security.config.bean.SecurityProperties;
import com.hengheng.service.LoginService;
import com.hengheng.service.OnlineUserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lkj
 * @Date 2025/5/22 14:47
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private RedisCache redisCache;
    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private OnlineUserInfoService onlineUserInfoService;

    /**
     * @param registerQuery
     * @return boolean
     * @description 注册账号
     * @author lkj
     * @date 2025/5/22
     */
    @Override
    public Boolean register(RegisterQuery registerQuery) {
        String encryptedPwd = passwordEncoder.encode(registerQuery.getPassword());
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        BeanUtils.copyProperties(registerQuery, userInfoEntity);
        userInfoEntity.setPassword(encryptedPwd);
        return userInfoRepository.saveRegister(userInfoEntity);
    }

    /**
     * @param loginQuery
     * @return
     * @description 登录
     * @author lkj
     * @date 2025/5/22
     */
    @Override
    public AjaxResult login(LoginQuery loginQuery,HttpServletRequest request) {
        String key = "captcha:" + loginQuery.getCaptchaUUID();
        String cachedCaptcha = redisCache.getCacheObject(key).toString();

        if (cachedCaptcha == null) {
            return AjaxResult.error("AjaxResult");
        }
        if (!cachedCaptcha.equalsIgnoreCase(loginQuery.getCode())) {
            return AjaxResult.error("验证码错误");
        }
        //验证成功删除缓存，避免重复使用
        redisCache.deleteObject(key);

        //验证账号密码
        //创建一个UsernamePasswordAuthenticationToken对象，将用户的用户名和密码作为参数传入。
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginQuery.getUserName(), loginQuery.getPassword());

        //调用authenticationManager.authenticate()方法对用户进行身份验证，返回一个Authentication对象。
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (ObjectUtil.isNull(authenticate)) {
            return AjaxResult.error("用户名或者密码错误");
        }
        //从authenticate对象中获取登录用户的信息。
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        UserInfoEntity userInfo = loginUser.getUserInfo();
        redisCache.setCacheObject("login:" + userInfo.getUserId().toString(), userInfo);

        //生成token
        String jwt = TokenUtil.createJWT(userInfo.getUserId().toString());
        Map<String, Object> data = new HashMap<>();
        data.put("token", securityProperties.getTokenStartWith() + jwt);

        //保存登录状态
        onlineUserInfoService.save(jwt, userInfo, request);
        return AjaxResult.success(data);
    }

    @Override
    public void loginOut(HttpServletRequest httpRequest) {
        String token = tokenUtil.getToken(httpRequest);
        String loginKey = tokenUtil.loginKey(token);
        redisCache.deleteObject(loginKey);
    }

}
