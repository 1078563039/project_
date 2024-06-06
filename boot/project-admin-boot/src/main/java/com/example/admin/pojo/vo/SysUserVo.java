package com.example.admin.pojo.vo;

import com.example.admin.pojo.entity.SysDeptEntity;
import com.example.common.base.entity.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 系统用户实体
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Data
@ToString
@ApiModel(description = "系统用户信息")
public class SysUserVo extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long deptId;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "用户昵称")
	private String nickName;

	@ApiModelProperty(value = "用户密码")
	private String passWord;

	@ApiModelProperty(value = "用户联系电话")
	private String mobile;

	@ApiModelProperty(value = "用户邮箱")
	private String email;

	@ApiModelProperty(value = "用户性别")
	private Integer sex;

	@ApiModelProperty(value = "用户状态", example = "1 NORMAL正常  0 PROHIBIT禁用")
	private Integer status;

	@ApiModelProperty("用户的角色编码集合")
	private List<SysRoleVo> sysRoleVoList;

	@ApiModelProperty("用户的部门信息")
	private SysDeptVo sysDeptVo;
}