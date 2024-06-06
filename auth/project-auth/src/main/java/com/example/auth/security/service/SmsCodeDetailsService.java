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

/**
 * @author dzk
 * @date Created in 2020/5/20 21:41
 */
@Service
public class SmsCodeDetailsService implements UserDetailsService {

    @Resource
    private SysUserFeignApi sysUserFeignApi;

    /**
     * 查询用户信息
     */
    @Override
    public SelfUserEntity loadUserByUsername(String mobile) throws UsernameNotFoundException {
        BaseResult<SysUserDto> result = sysUserFeignApi.selectByMobile(mobile);
        SysUserDto sysUserDto = result.getData();
        if (Objects.nonNull(sysUserDto)){
            SelfUserEntity selfUserEntity = new SelfUserEntity();
            BeanUtils.copyProperties(sysUserDto, selfUserEntity);
            selfUserEntity.setUsername(sysUserDto.getUserName());
            selfUserEntity.setPassword(sysUserDto.getPassWord());
            return selfUserEntity;
        }
        return null;
    }
}
