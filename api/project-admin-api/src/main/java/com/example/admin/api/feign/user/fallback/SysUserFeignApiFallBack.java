package com.example.admin.api.feign.user.fallback;

import com.example.admin.api.feign.user.SysUserFeignApi;
import com.example.admin.api.feign.user.dto.SysUserDto;
import com.example.common.base.result.BaseResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class SysUserFeignApiFallBack implements SysUserFeignApi {

    @Override
    public BaseResult<SysUserDto> selectByUserName(String userName) {
        return null;
    }

    @Override
    public BaseResult<SysUserDto> selectByMobile(String mobile) {
        return null;
    }


}
