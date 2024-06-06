package com.example.admin.domain;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.api.feign.dept.dto.SysDeptDto;
import com.example.admin.pojo.vo.SysDeptVo;
import com.example.admin.pojo.vo.SysUserVo;
import com.example.admin.result.AdminResult;
import com.example.admin.service.SysDeptService;
import com.example.admin.service.SysUserService;
import com.example.common.base.result.BaseResult;
import com.example.common.base.result.exception.BaseException;
import com.example.common.utils.list.Tree2List;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@GlobalTransactional
public class SysDeptDomain {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysUserService sysUserService;

    public List<SysDeptVo> tree(Long pid) {
        return sysDeptService.tree(pid);
    }

    /**
     * 保存 SysDeptVo 对象。
     *
     * @param sysDeptVo SysDeptVo 对象
     * @return          保存成功的记录数
     */
    public Integer add(SysDeptVo sysDeptVo) {
        deptOperateValid(sysDeptVo);
        return sysDeptService.add(sysDeptVo);
    }

    /**
     * 更新 SysDeptVo 对象。
     *
     * @param sysDeptVo SysDeptVo 对象
     * @return          更新成功的记录数
     */
    public Integer edit(SysDeptVo sysDeptVo) {
        deptOperateValid(sysDeptVo);
        return sysDeptService.edit(sysDeptVo);
    }

    private void deptOperateValid(SysDeptVo sysDeptVo) {
        // 如果pid为空，则默认为0
        if (sysDeptVo.getPid() == null){
            sysDeptVo.setPid(0L);
        }

        //判断部门名称是否已经存在
        SysDeptVo deptByDeptName = sysDeptService.selectByName(sysDeptVo.getName());
        //判断部门编码是否已经存在
        SysDeptVo deptByDeptCode = sysDeptService.selectByCode(sysDeptVo.getCode());
        if (sysDeptVo.getId() == null) {
            //新增
            if (deptByDeptName != null) {
                throw new BaseException(AdminResult.DEPT_NAME_IS_EXIST);
            }
            if (deptByDeptCode != null) {
                throw new BaseException(AdminResult.DEPT_CODE_IS_EXIST);
            }
        } else {
            //修改
            if (deptByDeptName != null && !deptByDeptName.getId().equals(sysDeptVo.getId())) {
                throw new BaseException(AdminResult.DEPT_NAME_IS_EXIST);
            }
            if (deptByDeptCode != null && !deptByDeptCode.getId().equals(sysDeptVo.getId())) {
                throw new BaseException(AdminResult.DEPT_CODE_IS_EXIST);
            }
        }

        //判断父级部门是否存在,可以为0也就是顶级部门
        if (sysDeptVo.getPid() != 0L && sysDeptService.select(sysDeptVo.getPid()) == null) {
            throw new BaseException(AdminResult.DEPT_PID_IS_NOT_EXIST);
        }
        //父级部门存在，但是父级部门是自己
        if (sysDeptVo.getPid() != 0L && sysDeptVo.getPid().equals(sysDeptVo.getId())) {
            throw new BaseException(AdminResult.DEPT_PID_IS_SELF);
        }
        //父级部门存在，但是父级部门是自己的子部门
        List<SysDeptVo> sysDeptVoTree = tree(sysDeptVo.getPid());
        List<SysDeptVo> sysDeptVoList = Tree2List.tree2List(sysDeptVoTree, SysDeptVo::getChildren);
        if (CollectionUtil.isNotEmpty(sysDeptVoList)) {
            if (sysDeptVoList.stream().anyMatch(deptVo -> deptVo.getId().equals(sysDeptVo.getPid()))){
                throw new BaseException(AdminResult.DEPT_PID_IS_SELF_CHILDREN);
            }
        }
    }

    public Integer delete(Long id) {
        //判断是否存在关联的用户信息
        List<SysUserVo> sysUserVoList = sysUserService.selectByDeptId(id);
        if (CollectionUtil.isNotEmpty(sysUserVoList)) {
            throw new BaseException(AdminResult.DEPT_IS_EXIST_USER);
        }
        return sysDeptService.delete(id);
    }

    public SysDeptVo select(Long deptId) {
        SysDeptVo sysDeptVo = sysDeptService.select(deptId);
        //如果父级部门不是顶级部门，则查询父级部门名称
        if (sysDeptVo.getPid() != 0L) {
            SysDeptVo pSysDeptVo = sysDeptService.select(sysDeptVo.getPid());
            sysDeptVo.setPDeptName(pSysDeptVo.getName());
        }
        return sysDeptVo;
    }
}
