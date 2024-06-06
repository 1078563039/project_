package com.example.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.api.feign.user.dto.SysUserDto;
import com.example.admin.pojo.entity.SysUserEntity;
import com.example.admin.pojo.vo.SysUserVo;

import java.util.List;

/**
 * @Description 系统用户业务接口
 * @Author Sans
 * @CreateTime 2019/6/14 15:57
 */
public interface SysUserService extends IService<SysUserEntity> {

    Page<SysUserVo> page(Integer pageNum, Integer pageSize, String userName, Long deptId);

    Integer add(SysUserVo sysUserVo);

    Integer edit(SysUserVo sysUserVo);

    Integer delete(Long id);

    SysUserVo select(Long id);

    SysUserVo selectByUserName(String userName);

    SysUserVo selectByMobile(String mobile);

    List<Long> selectRoleId(Long userId);

    Integer assignRole(Long userId, List<Long> roleIdList);


    /**
     * 通过部门id查询所有的用户信息
     * @param id 部门id
     * @return
     */
    List<SysUserVo> selectByDeptId(Long id);
}