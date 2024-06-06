package com.example.admin.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.pojo.vo.SysRoleVo;
import com.example.admin.service.SysRoleService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@GlobalTransactional
public class SysRoleDomain {

    @Autowired
    private SysRoleService sysRoleService;

    public Page<SysRoleVo> page(Integer pageNum, Integer pageSize, String roleName) {
        return sysRoleService.page(pageNum, pageSize, roleName);
    }

    public Integer add(SysRoleVo sysRoleVo) {
        return sysRoleService.add(sysRoleVo);
    }

    public Integer edit(SysRoleVo sysRoleVo) {
        return sysRoleService.edit(sysRoleVo);
    }

    public Integer delete(Long id) {
        return sysRoleService.delete(id);
    }

    public SysRoleVo select(Long id) {
        return sysRoleService.select(id);
    }

    public List<SysRoleVo> selectByUserId(Long userId) {
        return sysRoleService.selectByUserId(userId);
    }
}
