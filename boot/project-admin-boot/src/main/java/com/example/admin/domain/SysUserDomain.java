package com.example.admin.domain;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.api.feign.user.dto.SysUserDto;
import com.example.admin.pojo.entity.SysUserEntity;
import com.example.admin.pojo.entity.elstic.SysUserEntityElastic;
import com.example.admin.pojo.vo.SysUserVo;
import com.example.admin.result.AdminResult;
import com.example.admin.service.SysDeptService;
import com.example.admin.service.SysRoleService;
import com.example.admin.service.SysUserService;
import com.example.admin.service.elastic.SysUserElasticService;
import com.example.common.base.result.exception.BaseException;
import io.seata.spring.annotation.GlobalTransactional;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@GlobalTransactional
public class SysUserDomain {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysDeptDomain sysDeptDomain;

    public Page<SysUserVo> page(Integer pageNum, Integer pageSize, String userName, Long deptId) {
        Page<SysUserVo> page = sysUserService.page(pageNum, pageSize, userName, deptId);
        List<SysUserVo> recordList = page.getRecords();    //获取当前页的数据
        recordList.forEach(sysUserVo -> {
            sysUserVo.setSysDeptVo(sysDeptService.select(sysUserVo.getDeptId()));
            sysUserVo.setSysRoleVoList(sysRoleService.selectByUserId(sysUserVo.getId()));
        });
        return page;
    }

    public Integer add(SysUserVo sysUserVo) {
        //判断用户的手机和姓名是否已经存在
        if (sysUserService.selectByUserName(sysUserVo.getUserName()) != null) {
            throw new BaseException(AdminResult.USER_USERNAME_IS_EXIST);
        }
        if (sysUserService.selectByMobile(sysUserVo.getMobile()) != null) {
            throw new BaseException(AdminResult.USER_MOBILE_IS_EXIST);
        }

        //判断部门是否存在
        if (sysDeptDomain.select(sysUserVo.getDeptId()) == null) {
            throw new BaseException(AdminResult.DEPT_IS_NOT_EXIST);
        }
        return sysUserService.add(sysUserVo);
    }

    public Integer edit(SysUserVo sysUserVo) {
        //判断用户的手机和姓名是否已经存在
        SysUserVo userByUserName = sysUserService.selectByUserName(sysUserVo.getUserName());
        if (userByUserName != null && !userByUserName.getId().equals(sysUserVo.getId())) {
            throw new BaseException(AdminResult.USER_USERNAME_IS_EXIST);
        }
        SysUserVo userByMobile = sysUserService.selectByMobile(sysUserVo.getMobile());
        if (userByMobile != null && !userByMobile.getId().equals(sysUserVo.getId())) {
            throw new BaseException(AdminResult.USER_MOBILE_IS_EXIST);
        }

        //判断部门是否存在
        if (sysDeptDomain.select(sysUserVo.getDeptId()) == null) {
            throw new BaseException(AdminResult.DEPT_IS_NOT_EXIST);
        }

        return sysUserService.edit(sysUserVo);
    }

    public Integer delete(Long id) {
        //判断用户是否存在
        if (sysUserService.getById(id) == null) {
            throw new BaseException(AdminResult.USER_IS_NOT_EXIST);
        }
        //admin用户不允许删除
        if (id.equals(1L)) {
            throw new BaseException(AdminResult.USER_ADMIN_NOT_DELETE);
        }
        return sysUserService.delete(id);
    }

    public SysUserVo select(Long id) {
        return sysUserService.select(id);
    }

    public SysUserVo selectByUserName(String userName) {
        return sysUserService.selectByUserName(userName);
    }

    public SysUserVo selectByMobile(String mobile) {
        return sysUserService.selectByMobile(mobile);
    }

    public List<Long> selectRoleId(Long userId) {
        //判断用户是否存在
        if (sysUserService.getById(userId) == null) {
            throw new BaseException(AdminResult.USER_IS_NOT_EXIST);
        }
        return sysUserService.selectRoleId(userId);
    }

    public Integer assignRole(Long userId, List<Long> roleIdList) {
        //判断用户是否存在
        if (sysUserService.getById(userId) == null) {
            throw new BaseException(AdminResult.USER_IS_NOT_EXIST);
        }
        //判断角色是否存在
        if (CollUtil.isEmpty(roleIdList) || sysRoleService.selectByIdList(roleIdList) == null) {
            throw new BaseException(AdminResult.ROLE_IS_NOT_EXIST);
        }

        return sysUserService.assignRole(userId, roleIdList);
    }
}
