package com.example.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.converter.SysDeptConverter;
import com.example.admin.dao.SysDeptDao;
import com.example.admin.pojo.entity.SysDeptEntity;
import com.example.admin.pojo.vo.SysDeptVo;
import com.example.admin.service.SysDeptService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * SysDeptServiceImpl 是 SysDeptService 接口的实现类。
 * 它提供了对 SysDeptEntity 对象执行 CRUD 操作的方法。
 */
@Service("sysDeptService")
@GlobalTransactional
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    @Resource
    private SysDeptDao sysDeptDao;

    /**
     * 根据指定条件获取 SysDeptVo 对象的列表。
     *
     * @param pid       要搜索的父级部门 ID
     * @return          包含 SysDeptVo 对象的列表
     */
    @Override
    public List<SysDeptVo> tree(Long pid) {
        List<SysDeptEntity> sysDeptEntityList = sysDeptDao.selectTree(pid);
        return SysDeptConverter.INSTANCE.entityToVoList(sysDeptEntityList);
    }

    /**
     * 保存 SysDeptVo 对象。
     *
     * @param sysDeptVo SysDeptVo 对象
     * @return          保存成功的记录数
     */
    @Override
    public Integer add(SysDeptVo sysDeptVo) {
        SysDeptEntity sysDeptEntity = SysDeptConverter.INSTANCE.voToEntity(sysDeptVo);
        return sysDeptDao.insert(sysDeptEntity);
    }

    /**
     * 更新 SysDeptVo 对象。
     *
     * @param sysDeptVo SysDeptVo 对象
     * @return          更新成功的记录数
     */
    @Override
    public Integer edit(SysDeptVo sysDeptVo) {
        //更新部门信息
        SysDeptEntity sysDeptEntity = SysDeptConverter.INSTANCE.voToEntity(sysDeptVo);
        return sysDeptDao.updateById(sysDeptEntity);
    }

    /**
     * 根据部门 ID 删除部门。
     *
     * @param id    部门 ID
     * @return      删除成功的记录数
     */
    @Override
    public Integer delete(Long id) {
        return sysDeptDao.deleteById(id);
    }

    @Override
    public SysDeptVo select(Long deptId) {
        SysDeptEntity sysDeptEntity = sysDeptDao.selectById(deptId);
        return SysDeptConverter.INSTANCE.entityToVo(sysDeptEntity);
    }

    @Override
    public SysDeptVo selectByCode(String code) {
        LambdaQueryWrapper<SysDeptEntity> queryWrapper = new LambdaQueryWrapper<>(SysDeptEntity.class)
                .eq(StrUtil.isNotBlank(code), SysDeptEntity::getCode, code);
        SysDeptEntity sysDeptEntity = sysDeptDao.selectOne(queryWrapper);
        return SysDeptConverter.INSTANCE.entityToVo(sysDeptEntity);
    }

    @Override
    public SysDeptVo selectByName(String name) {
        LambdaQueryWrapper<SysDeptEntity> queryWrapper = new LambdaQueryWrapper<>(SysDeptEntity.class)
                .eq(StrUtil.isNotBlank(name), SysDeptEntity::getName, name);
        SysDeptEntity sysDeptEntity = sysDeptDao.selectOne(queryWrapper);
        return SysDeptConverter.INSTANCE.entityToVo(sysDeptEntity);
    }

}
