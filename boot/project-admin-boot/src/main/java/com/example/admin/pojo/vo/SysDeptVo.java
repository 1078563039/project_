package com.example.admin.pojo.vo;

import com.example.admin.pojo.entity.SysDeptEntity;
import com.example.common.base.entity.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "系统部门信息")
public class SysDeptVo extends BaseVo {

    private Long id;

    private Long pid;

    private String pDeptName;

    private Long likeId;

    private Long managerId;

    private String name;

    private String code;

    private String address;

    private String phone;

    private Integer orderNum;

    private Integer open;

    private List<SysDeptVo> children;

}
