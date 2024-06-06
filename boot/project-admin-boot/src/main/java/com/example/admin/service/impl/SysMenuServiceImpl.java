package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.converter.SysMenuConverter;
import com.example.admin.dao.SysMenuDao;
import com.example.admin.pojo.entity.SysMenuEntity;
import com.example.admin.pojo.vo.SysMenuVo;
import com.example.admin.service.SysMenuService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description 权限业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysMenuService")
@GlobalTransactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;

    @Override
    public List<SysMenuVo> tree(Long pid) {
        List<SysMenuEntity> sysMenuEntityList = sysMenuDao.selectTree(pid);
        return SysMenuConverter.INSTANCE.entityToVoList(sysMenuEntityList);
    }

    @Override
    public Integer add(SysMenuVo sysMenuVo) {
        SysMenuEntity sysMenuEntity = SysMenuConverter.INSTANCE.voToEntity(sysMenuVo);
        return sysMenuDao.insert(sysMenuEntity);
    }

    @Override
    public Integer edit(SysMenuVo sysMenuVo) {
        SysMenuEntity sysMenuEntity = SysMenuConverter.INSTANCE.voToEntity(sysMenuVo);
        return sysMenuDao.updateById(sysMenuEntity);
    }

    @Override
    public Integer delete(Long id) {
        return sysMenuDao.deleteById(id);
    }

    @Override
    public SysMenuVo select(Long id) {
        SysMenuEntity sysMenuEntity = sysMenuDao.selectById(id);
        return SysMenuConverter.INSTANCE.entityToVo(sysMenuEntity);
    }

    @Override
    public SysMenuVo selectByName(String name) {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<SysMenuEntity>().lambda();
        queryWrapper.eq(SysMenuEntity::getName, name);
        SysMenuEntity sysMenuEntity = sysMenuDao.selectOne(queryWrapper);
        return SysMenuConverter.INSTANCE.entityToVo(sysMenuEntity);
    }

    @Override
    public List<SysMenuVo> assignList(Long roleId) {
        List<SysMenuEntity> sysMenuEntityList = sysMenuDao.assignList(roleId);
        return SysMenuConverter.INSTANCE.entityToVoList(sysMenuEntityList);
    }

    @Override
    public Integer assignSave(Long roleId, List<Long> menuIdList) {
        return sysMenuDao.assignSave(roleId, menuIdList);
    }

    @Override
    public List<SysMenuVo> routes(Long userId) {
        List<SysMenuEntity> sysMenuEntityList = sysMenuDao.routes(userId);

        for (int i = sysMenuEntityList.size() - 1; i >= 0; i--) {
            SysMenuEntity routerVo = sysMenuEntityList.get(i);

            // 判断是否存在父节点
            List<SysMenuEntity> collect = sysMenuEntityList.stream()
                    .filter(o -> o.getId().equals(routerVo.getPid()))
                    .collect(Collectors.toList());

            collect.forEach(o -> {
                if (o.getChildren() == null || o.getChildren().isEmpty())
                    o.setChildren(new ArrayList<>());
                o.getChildren().add(routerVo);
            });

            if (!collect.isEmpty()){
                sysMenuEntityList.remove(i);
            }
        }

        return SysMenuConverter.INSTANCE.entityToVoList(sysMenuEntityList);
    }

    @Override
    public List<SysMenuVo> permissions(Long userId) {
        List<SysMenuEntity> sysMenuEntityList = sysMenuDao.permissions(userId);
        return SysMenuConverter.INSTANCE.entityToVoList(sysMenuEntityList);
    }

}