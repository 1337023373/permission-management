package com.hengheng.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/4 17:13
 * @Version 1.0
 */
@Data
public class MenuTreeVO implements Serializable {
    private Long menuId;

    private List<MenuTreeVO> children;

    private Integer type;

    private String permission;

    private String title;

    private Integer menuSort;

    private String path;

    private String component;

    private Long pid;

    private Integer subCount;

    private Boolean iFrame;

    private Boolean cache;

    private Boolean hidden;

    private String componentName;

    private String icon;

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }

    public String getLabel() {
        return title;
    }
}
