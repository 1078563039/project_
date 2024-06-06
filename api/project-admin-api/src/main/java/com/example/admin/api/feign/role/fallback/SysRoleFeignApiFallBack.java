package com.example.admin.api.feign.role.fallback;

import com.example.admin.api.feign.role.SysRoleFeignApi;
import com.example.admin.api.feign.role.dto.SysRoleDto;
import com.example.common.base.result.BaseResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SysRoleFeignApiFallBack implements SysRoleFeignApi {

    @Override
    public BaseResult<List<SysRoleDto>> selectByUserId(Long userId) {
        return null;
    }
}
