package com.example.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.converter.SysRoleConverter;
import com.example.admin.converter.SysUserConverter;
import com.example.admin.dao.SysRoleDao;
import com.example.admin.pojo.entity.SysRoleEntity;
import com.example.admin.pojo.entity.SysUserEntity;
import com.example.admin.pojo.vo.SysRoleVo;
import com.example.admin.service.SysRoleService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Description 角色业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysRoleService")
@GlobalTransactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public Page<SysRoleVo> page(Integer pageNum, Integer pageSize, String roleName) {
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = new LambdaQueryWrapper<>(SysRoleEntity.class);
        queryWrapper.like(StrUtil.isNotBlank(roleName), SysRoleEntity::getRoleName, roleName);
        Page<SysRoleEntity> page =  sysRoleDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        return SysRoleConverter.INSTANCE.entityToVoPage(page);
    }

    @Override
    public Integer add(SysRoleVo sysRoleVo) {
        SysRoleEntity sysRoleEntity = SysRoleConverter.INSTANCE.voToEntity(sysRoleVo);
        return sysRoleDao.insert(sysRoleEntity);
    }

    @Override
    public Integer edit(SysRoleVo sysRoleVo) {
        SysRoleEntity sysRoleEntity = SysRoleConverter.INSTANCE.voToEntity(sysRoleVo);
        return sysRoleDao.updateById(sysRoleEntity);
    }

    @Override
    public Integer delete(Long id) {
        return sysRoleDao.deleteById(id);
    }

    @Override
    public SysRoleVo select(Long id) {
        return SysRoleConverter.INSTANCE.entityToVo(sysRoleDao.selectById(id));
    }

    @Override
    public List<SysRoleVo> selectByUserId(Long userId) {
        List<SysRoleEntity> sysRoleEntityList = sysRoleDao.selectByUserId(userId);
        return SysRoleConverter.INSTANCE.entityToVoList(sysRoleEntityList);
    }

    @Override
    public List<SysRoleVo> selectByIdList(List<Long> roleList) {
        List<SysRoleEntity> sysRoleEntityList = sysRoleDao.selectBatchIds(roleList);
        return SysRoleConverter.INSTANCE.entityToVoList(sysRoleEntityList);
    }
}