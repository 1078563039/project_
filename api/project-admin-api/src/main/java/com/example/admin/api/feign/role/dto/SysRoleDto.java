package com.example.admin.api.feign.role.dto;

import com.example.admin.api.feign.menu.dto.SysMenuDto;
import com.example.common.base.entity.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysRoleDto extends BaseDto {

    public Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色备注")
    private String roleRemark;

    @ApiModelProperty(value = "菜单列表")
    private List<SysMenuDto> sysMenuDtoList;

}
