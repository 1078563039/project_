package com.example.admin.domain;

import cn.hutool.core.collection.CollectionUtil;
import com.example.admin.enums.menu.MenuLabelEnum;
import com.example.admin.pojo.vo.SysMenuVo;
import com.example.admin.result.AdminResult;
import com.example.admin.service.SysMenuService;
import com.example.admin.service.SysRoleService;
import com.example.common.base.result.exception.BaseException;
import com.example.common.utils.list.Tree2List;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@GlobalTransactional
public class SysMenuDomain {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleService sysRoleService;

    public List<SysMenuVo> tree(Long pid) {
        return sysMenuService.tree(pid);
    }

    public Integer add(SysMenuVo sysMenuVo) {
        menuOperateValid(sysMenuVo);
        return sysMenuService.add(sysMenuVo);
    }

    public Integer edit(SysMenuVo sysMenuVo) {
        menuOperateValid(sysMenuVo);
        return sysMenuService.edit(sysMenuVo);
    }

    private void menuOperateValid(SysMenuVo sysMenuVo) {
        //判断菜单的类型
        Integer label = sysMenuVo.getLabel();
        if (MenuLabelEnum.CATALOG.getKey().equals(label)){
            //如果是目录，设置父级为0
            sysMenuVo.setPid(0L);
        }else if (MenuLabelEnum.MENU.getKey().equals(label)) {
            //如果是目录或者菜单，父级必须是目录
            SysMenuVo parent = sysMenuService.select(sysMenuVo.getPid());
            if (parent.getLabel() != 0) {
                throw new BaseException(AdminResult.MENU_PID_IS_NOT_DIR);
            }
        } else if (MenuLabelEnum.BUTTON.getKey().equals(label)) {
            //如果是按钮，父级必须是菜单
            SysMenuVo parent = sysMenuService.select(sysMenuVo.getPid());
            if (parent.getLabel() != 1) {
                throw new BaseException(AdminResult.MENU_PID_IS_NOT_MENU);
            }
        }

        //判断菜单名称是否已经存在
        SysMenuVo selectByName = sysMenuService.selectByName(sysMenuVo.getName());
        if (sysMenuVo.getId() == null) {
            //新增
            if (selectByName != null) {
                throw new BaseException(AdminResult.MENU_NAME_IS_EXIST);
            }
        } else {
            //修改
            if (selectByName != null && !Objects.equals(selectByName.getId(), sysMenuVo.getId())) {
                throw new BaseException(AdminResult.MENU_NAME_IS_EXIST);
            }
        }

        //判断pid是否存在
        if (sysMenuVo.getPid() != 0L && sysMenuService.select(sysMenuVo.getPid()) == null) {
            throw new BaseException(AdminResult.MENU_PID_IS_NOT_EXIST);
        }
        //判断pid是否是自己
        if (sysMenuVo.getPid() != 0L && sysMenuVo.getPid().equals(sysMenuVo.getId())) {
            throw new BaseException(AdminResult.MENU_PID_IS_SELF);
        }
        //父级菜单存在，但是父级菜单是自己的子菜单
        List<SysMenuVo> sysMenuVoTree = tree(sysMenuVo.getPid());
        List<SysMenuVo> sysMenuVoList = Tree2List.tree2List(sysMenuVoTree, SysMenuVo::getChildren);
        if (CollectionUtil.isNotEmpty(sysMenuVoList)) {
            if (sysMenuVoList.stream().anyMatch(menuVo -> menuVo.getId().equals(sysMenuVo.getPid()))){
                throw new BaseException(AdminResult.MENU_PID_IS_SELF_CHILDREN);
            }
        }
    }

    public Integer delete(Long id) {
        return sysMenuService.delete(id);
    }

    public SysMenuVo select(Long id) {
        return sysMenuService.select(id);
    }

    public List<SysMenuVo> assignList(Long roleId) {
        return sysMenuService.assignList(roleId);
    }

    public Integer assignSave(Long roleId, List<Long> menuIdList) {
        return sysMenuService.assignSave(roleId, menuIdList);
    }

    public List<SysMenuVo> routes(Long userId) {
        return sysMenuService.routes(userId);
    }

    public List<SysMenuVo> permissions(Long userId) {
        return sysMenuService.permissions(userId);
    }
}
