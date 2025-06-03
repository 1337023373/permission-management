package com.hengheng.security.config;

import com.hengheng.common.utils.RedisCache;
import com.hengheng.common.utils.TokenUtil;
import com.hengheng.pojo.entity.UserInfoEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author lkj
 * @Date 2025/6/3 14:29
 * @Version 1.0
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisCache redisCache;

    // 白名单路径：不需要校验 Token 的路径
    private static final List<String> WHITE_LIST = Arrays.asList(
            "/hh/login",
            "/hh/login/",
            "/hh/captcha",
            "/hh/captcha/",
            "/hh/captcha/image",
            "/swagger-ui/",
            "/swagger-ui/index.html",
            "/swagger-resources",
            "/swagger-resources/",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/",
            "/favicon.ico",
            "/error"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String url = httpServletRequest.getRequestURI();
        for (String white : WHITE_LIST) {
            if (url.startsWith(white)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }

        //获取请求头中的token
        String token = httpServletRequest.getHeader("Authorization");
        //检查token是否存在：如果token不存在，则直接放行请求，继续执行后续的过滤器或处理器。
        if(!StringUtils.hasText(token)){
            //放行操作
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        String userId;
        try {
            Claims claims = TokenUtil
                    .parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("非法的token");
        }

        //从Redis中获取用户信息：根据userId从Redis缓存中获取用户信息。
        UserInfoEntity userInfo = redisCache.getCacheObject("login:" + userId);
        if (Objects.isNull(userInfo)) {
            throw new RuntimeException("用户未登录");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfo, null, null);
        //存入SecurityContextHolder：将获取到的用户信息存入SecurityContextHolder中，以便后续的权限验证和授权操作。
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
