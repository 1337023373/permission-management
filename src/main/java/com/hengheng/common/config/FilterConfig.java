package com.hengheng.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Collections;

/**
 * @Author lkj
 * @Date 2025/5/23 15:38
 * @Version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final AuthApiProperties authApiProperties;

    @Bean
    public FilterRegistrationBean<AuthFilter> orderFilter1() {
        FilterRegistrationBean<AuthFilter> filter = new FilterRegistrationBean<>();
        filter.setName("auth-filter");
        // Set effect url
        filter.setUrlPatterns(Collections.singleton("/**"));
        // Set ignore url, when multiply the value spilt with ","
        filter.addInitParameter("excludedUris", authApiProperties.getFree());
        filter.setOrder(-1);
        filter.setFilter(new AuthFilter());
        return filter;
    }
}
