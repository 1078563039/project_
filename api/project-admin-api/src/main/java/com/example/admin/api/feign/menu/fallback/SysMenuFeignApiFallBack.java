package com.example.admin.api.feign.menu.fallback;

import com.example.admin.api.feign.menu.SysMenuFeignApi;
import com.example.admin.api.feign.menu.dto.SysMenuDto;
import com.example.common.base.result.BaseResult;
import org.springframework.stereotype.Component;

@Component
public class SysMenuFeignApiFallBack implements SysMenuFeignApi {

    @Override
    public BaseResult<SysMenuDto> selectMenuList() {
        return null;
    }

    @Override
    public BaseResult<SysMenuDto> selectMenuRouter() {
        return null;
    }
}
