package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.pojo.entity.SysMenuEntity;
import com.example.admin.pojo.vo.SysMenuVo;

import java.util.List;

/**
 * @Description 权限业务接口
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    List<SysMenuVo> tree(Long pid);

    Integer add(SysMenuVo sysMenuVo);

    Integer edit(SysMenuVo sysMenuVo);

    Integer delete(Long id);

    SysMenuVo select(Long id);

    SysMenuVo selectByName(String name);

    List<SysMenuVo> assignList(Long roleId);

    Integer assignSave(Long roleId, List<Long> menuIdList);

    List<SysMenuVo> routes(Long userId);

    List<SysMenuVo> permissions(Long userId);
}