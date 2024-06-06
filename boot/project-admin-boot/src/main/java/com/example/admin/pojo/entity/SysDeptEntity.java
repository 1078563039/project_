package com.example.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.base.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 部门实体
 */
@Data
@TableName("sys_dept")
public class SysDeptEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    @TableField(value = "pid")
    private Long pid;

    @TableField(value = "like_id")
    private Long likeId;

    @TableField(value = "manager_id")
    private Long managerId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "code")
    private String code;

    @TableField(value = "address")
    private String address;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "order_num")
    private Integer orderNum;

    @TableField(value = "open")
    private Integer open;

    @TableField(exist = false)
    private List<SysDeptEntity> children;

}

