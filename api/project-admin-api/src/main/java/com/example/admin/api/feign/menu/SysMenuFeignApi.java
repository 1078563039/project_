package com.example.admin.api.feign.menu;

import com.example.admin.api.feign.menu.dto.SysMenuDto;
import com.example.admin.api.feign.menu.fallback.SysMenuFeignApiFallBack;
import com.example.common.base.result.BaseResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "菜单")
@Component
@FeignClient(value = "project-admin", contextId = "menu", path = "/admin/sysMenu", fallback = SysMenuFeignApiFallBack.class)
public interface SysMenuFeignApi {

    /**
     * 查询菜单列表
     * @return
     */
    @GetMapping("/selectMenuList")
    BaseResult<SysMenuDto> selectMenuList();

    /**
     * 查询菜单路由
     * @return
     */
    @GetMapping("/selectMenuRouter")
    BaseResult<SysMenuDto> selectMenuRouter();

}
