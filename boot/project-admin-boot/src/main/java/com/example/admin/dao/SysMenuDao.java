package com.example.admin.dao;

import com.example.admin.pojo.entity.SysMenuEntity;
import com.example.common.mybatis.config.operate.RootMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限DAO
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Mapper
public interface SysMenuDao extends RootMapper<SysMenuEntity> {

    List<SysMenuEntity> selectTree(@Param("pid") Long pid);

    List<SysMenuEntity> assignList(@Param("roleId") Long roleId);

    Integer assignSave(@Param("roleId") Long roleId, @Param("menuIdList") List<Long> menuIdList);

    List<SysMenuEntity> routes(@Param("userId") Long userId);

    List<SysMenuEntity> permissions(@Param("userId") Long userId);
}