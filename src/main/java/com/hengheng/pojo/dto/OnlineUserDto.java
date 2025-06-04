package com.hengheng.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 登录态信息
 * @param
 * @author  lkj
 * @date  2025/6/4
 * @return
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 岗位
     */
    private String dept;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * IP
     */
    private String ip;

    /**
     * 地址
     */
    private String address;

    /**
     * token
     */
    private String key;

    /**
     * 登录时间
     */
    private Date loginTime;

}
