/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.hengheng.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hengheng.common.utils.enums.DataScopeEnum;
import com.hengheng.pojo.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 角色
 * @author Zheng Jie
 * @date 2018-11-22
 */
@EqualsAndHashCode(of = "roleId", callSuper = false)
@Data
@TableName("sys_role")
public class RoleEntity extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "ID", hidden = true)
    private Long roleId;

    @ApiModelProperty(value = "用户", hidden = true)
    private Set<UserInfoEntity> users;

    @ApiModelProperty(value = "菜单", hidden = true)
    private Set<MenuEntity> menus;

    @ApiModelProperty(value = "部门", hidden = true)
    private Set<DeptEntity> depts;

    @NotBlank
    @ApiModelProperty(value = "名称", hidden = true)
    private String name;

    @ApiModelProperty(value = "数据权限，全部 、 本级 、 自定义")
    private String dataScope = DataScopeEnum.THIS_LEVEL.getValue();

    @ApiModelProperty(value = "级别，数值越小，级别越大")
    private Integer level = 3;

    @ApiModelProperty(value = "描述")
    private String description;
}
