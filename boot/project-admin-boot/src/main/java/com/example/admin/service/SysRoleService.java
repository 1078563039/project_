package com.example.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.pojo.entity.SysRoleEntity;
import com.example.admin.pojo.vo.SysRoleVo;

import java.util.List;

/**
 * @Description 角色业务接口
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 分页查询角色列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<SysRoleVo> page(Integer pageNum, Integer pageSize, String roleName);

    /**
     * 新增角色
     * @param sysRoleVo
     * @return
     */
    Integer add(SysRoleVo sysRoleVo);

    /**
     * 编辑角色
     * @param sysRoleVo
     * @return
     */
    Integer edit(SysRoleVo sysRoleVo);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Integer delete(Long id);

    SysRoleVo select(Long id);

    /**
     * 通过userId查询用户角色
     * @param userId
     * @return
     */
    List<SysRoleVo> selectByUserId(Long userId);

    /**
     * 通过角色id查询角色信息
     * @param roleList
     * @return
     */
    List<SysRoleVo> selectByIdList(List<Long> roleList);
}