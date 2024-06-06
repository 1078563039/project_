package com.example.admin.dao;

import com.example.admin.pojo.entity.SysRoleEntity;
import com.example.common.mybatis.config.operate.RootMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 角色DAO
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Mapper
public interface SysRoleDao extends RootMapper<SysRoleEntity> {

    List<SysRoleEntity> selectByUserId(@Param("userId") Long userId);

}