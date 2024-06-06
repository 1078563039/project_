package com.example.admin.api.feign.dept.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SysDeptDto {

    @ApiModelProperty(value = "部门ID")
    private Long id;

//    @NotBlank(message = "部门父ID不能为空")
    @ApiModelProperty(value = "部门父ID")
    private Long pid;

    @ApiModelProperty(value = "部门父名称")
    private String pName;

    @ApiModelProperty(value = "likeId")
    private Long likeId;

    @ApiModelProperty(value = "部门负责人")
    private Long managerId;

    @NotBlank(message = "部门名称不能为空")
    @ApiModelProperty(value = "部门名称")
    private String name;

    @NotBlank(message = "部门编码不能为空")
    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "部门地址")
    private String address;

    @ApiModelProperty(value = "部门电话")
    private String phone;

    @ApiModelProperty(value = "部门序号")
    private Integer orderNum;

    @ApiModelProperty(value = "节点是否默认开启")
    private Integer open;

    @ApiModelProperty(value = "子节点")
    private List<SysDeptDto> children;

}
