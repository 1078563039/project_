package com.example.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.api.feign.user.dto.SysUserDto;
import com.example.admin.converter.SysDeptConverter;
import com.example.admin.converter.SysUserConverter;
import com.example.admin.dao.SysUserDao;
import com.example.admin.pojo.entity.SysDeptEntity;
import com.example.admin.pojo.entity.SysUserEntity;
import com.example.admin.pojo.entity.elstic.SysUserEntityElastic;
import com.example.admin.pojo.vo.SysUserVo;
import com.example.admin.service.SysUserService;
import com.example.admin.service.elastic.SysUserElasticService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Description 系统用户业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Slf4j
@Service("sysUserService")
//@GlobalTransactional
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysUserElasticService sysUserElasticService;

    @Override
    public Page<SysUserVo> page(Integer pageNum, Integer pageSize, String userName, Long deptId) {
        LambdaQueryWrapper<SysUserEntity> pageQueryWrapper = new QueryWrapper<SysUserEntity>().lambda()
                .like(StrUtil.isNotBlank(userName), SysUserEntity::getUserName, "%"+userName+"%")
                .eq(Objects.nonNull(deptId), SysUserEntity::getDeptId, deptId);
        Page<SysUserEntity> sysUserEntityPage =  sysUserDao.selectPage(new Page<>(pageNum, pageSize), pageQueryWrapper);


        org.springframework.data.domain.Page<SysUserEntityElastic> sysUserEntityElastics = sysUserElasticService.searchUsers(null, pageNum, pageSize);
        log.info(JSONObject.toJSONString(sysUserEntityElastics.getContent()));

        return SysUserConverter.INSTANCE.entityToVoPage(sysUserEntityPage);
    }

    @Override
    public Integer add(SysUserVo sysUserVo) {
        SysUserEntity sysUserEntity = SysUserConverter.INSTANCE.voToEntity(sysUserVo);
        sysUserEntity.setPassWord(new BCryptPasswordEncoder().encode(StrUtil.isBlank(sysUserEntity.getPassWord())?"111111":sysUserEntity.getPassWord()));
        return sysUserDao.insert(sysUserEntity);
    }

    @Override
    public Integer edit(SysUserVo sysUserVo) {
        SysUserEntity sysUserEntity = SysUserConverter.INSTANCE.voToEntity(sysUserVo);
        if (StrUtil.isNotBlank(sysUserEntity.getPassWord())) {
            //编辑不修改密码
            sysUserEntity.setPassWord(null);
        }
        return sysUserDao.updateById(sysUserEntity);
    }

    @Override
    public Integer delete(Long id) {
        return sysUserDao.deleteById(id);
    }

    @Override
    public SysUserVo select(Long id) {
        SysUserEntity sysUserEntity = sysUserDao.userSelectById(id);
        return SysUserConverter.INSTANCE.entityToVo(sysUserEntity);
    }

    @Override
    public SysUserVo selectByUserName(String userName) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>().lambda();
        queryWrapper.eq(SysUserEntity::getUserName, userName);
        SysUserEntity sysUserEntity = sysUserDao.selectOne(queryWrapper);
        return SysUserConverter.INSTANCE.entityToVo(sysUserEntity);
    }

    @Override
    public SysUserVo selectByMobile(String mobile) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>().lambda();
        queryWrapper.eq(SysUserEntity::getMobile, mobile);
        SysUserEntity sysUserEntity = sysUserDao.selectOne(queryWrapper);
        return SysUserConverter.INSTANCE.entityToVo(sysUserEntity);
    }

    @Override
    public List<Long> selectRoleId(Long userId) {
        return sysUserDao.selectRoleId(userId);
    }

    @Override
    public Integer assignRole(Long userId, List<Long> roleIdList) {
        return sysUserDao.assignRole(userId, roleIdList);
    }

    @Override
    public List<SysUserVo> selectByDeptId(Long id) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>().lambda()
                .eq(SysUserEntity::getDeptId, id);
        return SysUserConverter.INSTANCE.entityToVoList(sysUserDao.selectList(queryWrapper));
    }

}