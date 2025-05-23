package com.hengheng.common.config;


import com.hengheng.common.utils.TokenUtil;
import com.sun.net.httpserver.HttpExchange;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lkj
 * @Date 2025/5/23 15:40
 * @Version 1.0
 */

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        int status;
        String msg;
        String token = req.getHeader("Token");
        if (StringUtils.isNotBlank(token)) {
            boolean isExpired = false;
            try {
                TokenUtil.parseJWT(token);
            } catch (ExpiredJwtException e) {
                isExpired = true;
            }
            if (!isExpired) {
                filterChain.doFilter(req, servletResponse);
                return;
            } else {
                status = 203;
                msg = "Login expired.";
            }
        } else {
            status = 203;
            msg = "Please login and try again.";
        }
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        //ResultData<Object> result = new ResultData<>(status, msg, null);
        //response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
