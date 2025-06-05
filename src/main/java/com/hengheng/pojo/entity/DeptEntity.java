package com.hengheng.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hengheng.pojo.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author lkj
 * @Date 2025/6/4 15:15
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dept")
public class DeptEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //private Set<Role> roles;
    @ApiModelProperty
    private Long deptId;

    @ApiModelProperty(value = "排序")
    private Integer deptSort;

    @NotBlank
    @ApiModelProperty(value = "部门名称")
    private String name;

    @NotNull
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "上级部门")
    private Long pid;

    @ApiModelProperty(value = "子节点数目", hidden = true)
    private Integer subCount = 0;

    //
    //@Override
    //public int hashCode() {
    //    return Objects.hash(id, name);
    //}
}
