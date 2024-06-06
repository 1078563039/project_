package com.example.common.base.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

public class BaseEntity {

    @TableField(fill = FieldFill.INSERT)
    public String createBy;

    @TableField(fill = FieldFill.INSERT)
    public Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    public String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    public Date updateTime;

    @TableLogic
    public Integer isDelete;
}
