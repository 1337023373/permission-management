package com.hengheng.security.config.bean;

import com.hengheng.security.config.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author lkj
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //禁用CSRF（跨站请求伪造）保护，因为在无状态的JWT认证中，不需要使用CSRF保护。
        http.csrf().disable()
                //设置会话管理策略为无状态，即不创建和使用会话。
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //开始配置请求的授权规则
                .authorizeRequests()
                //指定/user/login路径可以匿名访问，即不需要身份验证
                .antMatchers(
                        //基础的swagger网址
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/v3/**",
                        "/error",

                        //登录和验证码接口
                        "/login",
                        "/login/**",
                        "/captcha/**"

                ).permitAll()
                //指定其他所有请求都需要进行身份验证。
                .anyRequest().authenticated()
        ;
        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 获取AuthenticationManager对象，以便在其他地方使用该对象进行身份验证
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //调用super.authenticationManagerBean()方法，该方法是WebSecurityConfigurerAdapter类中的一个方法，用于获取AuthenticationManager对象。
        return super.authenticationManagerBean();
    }
}