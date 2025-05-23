package com.hengheng.common.config;

import io.lettuce.core.dynamic.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lkj
 * @Date 2025/5/23 14:32
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableSwagger2
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthApiProperties authApiProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/hh/swagger-ui/**",
                "/hh/swagger-ui/**",
                "/hh/v3/api-docs/**",
                "/hh/swagger-resources/**",
                "/hh/webjars/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] freeResource = authApiProperties.getFree().trim().split(",");
        String[] userResource = authApiProperties.getUser().trim().split(",");
        String[] adminResource = authApiProperties.getAdmin().trim().split(",");
        http.authorizeRequests()
                // 设置免认证资源
                .antMatchers(freeResource).permitAll()
                //.antMatchers("/hh/swagger-ui/**",
                //        "/hh/v3/api-docs/**",
                //        "/hh/swagger-resources/**",
                //        "/hh/webjars/**").permitAll()
                //.antMatchers("/**").permitAll()
                // 为不同权限分配不同资源
                //.antMatchers(userResource).hasRole("USER")
                //.antMatchers(adminResource).hasRole("ADMIN")
                // 默认无定义资源都需认证
                .anyRequest().authenticated()
                // 自定义认证访问资源
                .and().formLogin().loginProcessingUrl("/api/auth/verify")
                // 未认证访问受限资源逻辑
                .and().exceptionHandling().authenticationEntryPoint(this::unAuthHandle)
                .and()
                .httpBasic()
                // 允许跨域
                .and().cors()
                // 关闭跨站攻击
                .and().csrf().disable();
    }

    private void unAuthHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().write("{\"code\":401,\"msg\":\"未认证\"}");
    }


}
