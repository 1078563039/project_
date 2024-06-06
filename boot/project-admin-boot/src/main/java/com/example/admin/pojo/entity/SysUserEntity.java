package com.example.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 系统用户实体
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */

@Data
@ToString
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {

	@TableId(type = IdType.AUTO)
	@TableField(value = "id")
	private Long id;

	/**
	 * 部门ID
	 */
	@TableField(value = "dept_id")
	private Long deptId;

	/**
	 * 用户名
	 */
	@TableField(value = "user_name")
	private String userName;

	/**
	 * 昵称
	 */
	@TableField(value = "nick_name")
	private String nickName;

	/**
	 * 密码
	 */
	@TableField(value = "pass_word")
	private String passWord;

	/**
	 * 手机号码
	 */
	@TableField(value = "mobile")
	private String mobile;

	/**
	 * 邮箱
	 */
	@TableField(value = "email")
	private String email;

	/**
	 * 性别
	 */
	@TableField(value = "sex")
	private Integer sex;

	/**
	 * 状态:1 NORMAL正常  0 PROHIBIT禁用
	 */
	@TableField(value = "status")
	private Integer status;

	@TableField(exist = false)
	private List<SysRoleEntity> sysRoleEntityList;

	@TableField(exist = false)
	private SysDeptEntity sysDeptEntity;

}