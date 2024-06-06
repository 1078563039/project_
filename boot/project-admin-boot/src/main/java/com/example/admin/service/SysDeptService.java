package com.example.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.api.feign.dept.dto.SysDeptDto;
import com.example.admin.pojo.entity.SysDeptEntity;
import com.example.admin.pojo.vo.SysDeptVo;

import java.util.List;

public interface SysDeptService extends IService<SysDeptEntity> {

    List<SysDeptVo> tree(Long pid);

    Integer add(SysDeptVo sysDeptVo);

    Integer edit(SysDeptVo sysDeptVo);

    Integer delete(Long id);

    SysDeptVo select(Long deptId);

    //部门编码查询
    SysDeptVo selectByCode(String code);

    //部门名称查询
    SysDeptVo selectByName(String name);
}


