package com.example.common.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class BaseVo {

    public String createBy;

    public Date createTime;

    public String updateBy;

    public Date updateTime;

    public Integer isDelete;
}
