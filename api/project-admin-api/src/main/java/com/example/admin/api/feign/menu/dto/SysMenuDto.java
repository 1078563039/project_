package com.example.admin.api.feign.menu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysMenuDto {

    @ApiModelProperty(value = "菜单ID")
    private Long id;

    @ApiModelProperty(value = "菜单父ID")
    private Long pid;

    @ApiModelProperty(value = "菜单标签")
    private Integer label;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "菜单文件路径")
    private String path;

    @ApiModelProperty(value = "菜单组件")
    private String component;

    @ApiModelProperty(value = "菜单权限标识")
    private String permission;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "图标")
    private String icon;

    private List<SysMenuDto> children;
}
