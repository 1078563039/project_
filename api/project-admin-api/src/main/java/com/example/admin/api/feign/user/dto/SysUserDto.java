package com.example.admin.api.feign.user.dto;

import com.example.admin.api.feign.dept.dto.SysDeptDto;
import com.example.admin.api.feign.role.dto.SysRoleDto;
import com.example.common.base.entity.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@Data
public class SysUserDto extends BaseDto {

    public Long id;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "部门ID")
    @NotNull(message = "部门信息不能为空")
    private Long deptId;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "用户密码")
//    @NotBlank(message = "用户密码不能为空")
    private String passWord;

    @ApiModelProperty(value = "用户联系电话")
    @NotBlank(message = "用户联系电话不能为空")
    private String mobile;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户性别")
    @NotNull(message = "用户性别不能为空")
    private Integer sex;

    @ApiModelProperty(value = "用户状态", example = "1 NORMAL正常  0 PROHIBIT禁用")
    private Integer status;

    @ApiModelProperty("用户的角色编码集合")
    private List<SysRoleDto> sysRoleDtoList;

    @ApiModelProperty("用户的部门信息")
    private SysDeptDto sysDeptDto;

}
