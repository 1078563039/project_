package com.example.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 角色实体
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Data
@TableName("sys_role")
@ApiModel(description = "系统角色信息")
public class SysRoleEntity extends BaseEntity {

	@TableId(type = IdType.AUTO)
	@TableField(value = "id")
	private Long id;

	/**
	 * 角色名称
	 */
	@TableField("role_name")
	private String roleName;

	/**
	 * 角色备注
	 */
	@TableField("role_remark")
	private String roleRemark;

}
