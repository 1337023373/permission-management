package com.hengheng.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/4 17:13
 * @Version 1.0
 */
@Data
public class DeptTreeVO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long deptId;
    private Integer deptSort;

    private String name;

    private Boolean enabled;

    private Long pid;

    private Integer subCount;

    //判断是否可以展开
    private Boolean hasChildren;
    private List<DeptTreeVO> children = new ArrayList<>();
}
