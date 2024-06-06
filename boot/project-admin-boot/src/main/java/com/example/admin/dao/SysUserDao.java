package com.example.admin.dao;

import com.example.admin.pojo.entity.SysUserEntity;
import com.example.common.mybatis.config.operate.RootMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 系统用户DAO
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Mapper
public interface SysUserDao extends RootMapper<SysUserEntity> {

    List<Long> selectRoleId(@Param("userId") Long userId);

    Integer assignRole(@Param("userId") Long userId, @Param("roleIdList") List<Long> roleIdList);

    SysUserEntity userSelectById(@Param("id") Long id);
}
