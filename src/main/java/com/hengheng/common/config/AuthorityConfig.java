package com.hengheng.common.config;

import com.hengheng.common.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lkj
 * @Date 2025/5/28 15:25
 * @Version 1.0
 */
@Service("el")
public class AuthorityConfig {
    public Boolean check(String... permission) {
        // 获取当前用户的所有权限
        List<String> currentUserDataScope = SecurityUtils.getCurrentUser().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return currentUserDataScope.contains("admin") || Arrays.stream(permission).anyMatch(currentUserDataScope::contains);
    }
}
