package com.example.auth.security.service;

import com.example.admin.api.feign.user.SysUserFeignApi;
import com.example.admin.api.feign.user.dto.SysUserDto;
import com.example.auth.security.entity.SelfUserEntity;
import com.example.common.base.result.BaseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class BaseUserDetailService implements UserDetailsService {

    @Resource
    private SysUserFeignApi sysUserFeignApi;

    @Override
    public SelfUserEntity loadUserByUsername(String name) throws UsernameNotFoundException {
        //通过用户名称查询用户信息
        BaseResult<SysUserDto> result = sysUserFeignApi.selectByUserName(name);
        SelfUserEntity selfUserEntity = new SelfUserEntity();
        SysUserDto sysUserDto = result.getData();
        if (Objects.nonNull(sysUserDto)){
            BeanUtils.copyProperties(sysUserDto, selfUserEntity);
            selfUserEntity.setUserId(sysUserDto.getId());
            selfUserEntity.setUsername(sysUserDto.getUserName());
            selfUserEntity.setPassword(sysUserDto.getPassWord());
            return selfUserEntity;
        }

        return null;
    }
}
