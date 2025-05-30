package com.hengheng.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengheng.common.exception.BadRequestException;
import com.hengheng.common.utils.enums.DataScopeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lkj
 * @Date 2025/5/28 14:19
 * @Version 1.0
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前登录的用户
     *
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(getCurrentUsername());
    }


    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    private static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "找不到当前登录信息");
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.valueToTree(userDetails);
        return jsonNode.get("user").get("id").asLong();
    }

    /**
     * 获取当前用户的数据权限
     *
     * @return /
     */
    public static List<Long> getCurrentUserDataScope() {
        UserDetails userDetails = getCurrentUser();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.valueToTree(userDetails);
        JsonNode dataScope = jsonNode.get("dataScope");
        if (dataScope == null || !dataScope.isArray()) {
            return new ArrayList<Long>();
        }
        return mapper.convertValue(dataScope, new TypeReference<ArrayList<Long>>() {});
    }

    /**
     * 获取数据权限级别
     *
     * @return 级别
     */
    public static String getDataScope(){
        List<Long> dataScopes = getCurrentUserDataScope();
        if (!dataScopes.isEmpty()) {
            return "";
        }
        return DataScopeEnum.ALL.getValue();
    }
}
