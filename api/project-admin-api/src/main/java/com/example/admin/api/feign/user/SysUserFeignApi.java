package com.example.admin.api.feign.user;

import com.example.admin.api.feign.user.dto.SysUserDto;
import com.example.admin.api.feign.user.fallback.SysUserFeignApiFallBack;
import com.example.common.base.result.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "用户")
@Component
@FeignClient(value = "project-admin", contextId = "user", path = "/admin/sysUser", fallback = SysUserFeignApiFallBack.class)
public interface SysUserFeignApi {

    @ApiOperation("通过用户名查询用户")
    @GetMapping("/selectByUserName")
    BaseResult<SysUserDto> selectByUserName(@RequestParam("userName") String userName);

    @ApiOperation("通过手机号查询用户")
    @GetMapping("/selectByMobile")
    BaseResult<SysUserDto> selectByMobile(@RequestParam("mobile") String mobile);

}
