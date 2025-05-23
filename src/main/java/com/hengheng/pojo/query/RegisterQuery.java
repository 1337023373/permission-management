package com.hengheng.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Author lkj
 * @Date 2025/5/22 14:22
 * @Version 1.0
 */
@Data
public class RegisterQuery {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3~20之间")
    private String username;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    @Size(max = 30, message = "昵称长度不能超过30个字符")
    private String nickName;

    @ApiModelProperty("性别")
    @Pattern(regexp = "^[男女]$", message = "性别只能为 男 或 女")
    private String gender;

    @ApiModelProperty("手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty("头像")
    private String url;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6~20之间")
    private String password;
}
