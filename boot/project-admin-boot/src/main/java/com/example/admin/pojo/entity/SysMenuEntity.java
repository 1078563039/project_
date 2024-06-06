package com.example.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 权限实体
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Data
@TableName("sys_menu")
public class SysMenuEntity extends BaseEntity {

	@TableId(type = IdType.AUTO)
	@TableField(value = "id")
	private Long id;

	@TableField(value = "pid")
	private Long pid;

	@TableField(value = "label")
	private Integer label;

	@TableField(value = "path")
	private String path;

	@TableField(value = "path_component")
	private String component;

	@TableField(value = "permission")
	private String permission;

	@TableField(value = "name")
	private String name;

	@TableField(value = "title")
	private String title;

	@TableField(value = "level")
	private Integer level;

	@TableField(value = "icon")
	private String icon;

	@TableField(exist = false)
	private List<SysMenuEntity> children;

}
