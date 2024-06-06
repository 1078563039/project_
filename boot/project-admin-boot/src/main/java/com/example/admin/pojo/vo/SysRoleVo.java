package com.example.admin.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.example.common.base.entity.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 角色实体
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Data
@ApiModel(description = "系统角色信息")
public class SysRoleVo extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 角色名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 角色备注
	 */
	@ApiModelProperty(value = "角色备注")
	private String roleRemark;
}
