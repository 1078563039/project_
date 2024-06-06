package com.example.admin.api.feign.role;

import com.example.admin.api.feign.role.dto.SysRoleDto;
import com.example.admin.api.feign.role.fallback.SysRoleFeignApiFallBack;
import com.example.common.base.result.BaseResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "角色")
@Component
@FeignClient(value = "project-admin", contextId = "role", path = "/admin/sysRole", fallback = SysRoleFeignApiFallBack.class)
public interface SysRoleFeignApi {

    @GetMapping("/selectByUserId")
    BaseResult<List<SysRoleDto>> selectByUserId(@RequestParam("userId") Long userId);

}
