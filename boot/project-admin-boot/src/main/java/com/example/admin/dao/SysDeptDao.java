package com.example.admin.dao;

import com.example.admin.pojo.entity.SysDeptEntity;
import com.example.common.mybatis.config.operate.RootMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptDao extends RootMapper<SysDeptEntity> {

    List<SysDeptEntity> selectTree(@Param("pid") Long pid);

}
