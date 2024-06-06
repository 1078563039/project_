package com.example.admin.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.admin.pojo.entity.SysMenuEntity;
import com.example.common.base.entity.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 权限实体
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Data
@ApiModel(description = "系统菜单信息")
public class SysMenuVo extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 权限ID
	 */
	@TableId
	@ApiModelProperty(value = "菜单ID")
	private Long id;

	@ApiModelProperty(value = "菜单父ID")
	private Long pid;

	@ApiModelProperty(value = "菜单标签")
	private Integer label;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "菜单文件路径")
	private String path;

	@ApiModelProperty(value = "菜单组件")
	private String component;
	/**
	 * 权限标识
	 */
	@ApiModelProperty(value = "菜单权限标识")
	private String permission;

	private String title;

	private Integer level;

	private String icon;

	private List<SysMenuVo> children;
}
