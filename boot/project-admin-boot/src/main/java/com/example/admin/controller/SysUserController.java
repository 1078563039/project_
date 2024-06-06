package com.example.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.api.feign.role.dto.SysRoleDto;
import com.example.admin.api.feign.user.SysUserFeignApi;
import com.example.admin.api.feign.user.dto.SysUserDto;
import com.example.admin.converter.SysUserConverter;
import com.example.admin.domain.SysUserDomain;
import com.example.admin.pojo.vo.SysUserVo;
import com.example.common.base.result.BaseResult;
import com.example.common.utils.content.UserContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/sysUser")
public class SysUserController implements SysUserFeignApi {

    @Autowired
    private SysUserDomain sysUserDomain;

    @GetMapping("/page")
    public BaseResult<Page<SysUserDto>> page(@RequestParam(defaultValue = "1") Integer currentPage,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false, name = "userName") String userName,
                                                 @RequestParam(required = false, name = "deptId") Long deptId){
        Page<SysUserVo> page = sysUserDomain.page(currentPage, pageSize, userName, deptId);
        return BaseResult.ok(SysUserConverter.INSTANCE.voToDtoPage(page));
    }

    @PostMapping
    public BaseResult<Integer> add(@RequestBody @Valid SysUserDto sysUserDto){
        return BaseResult.ok(sysUserDomain.add(SysUserConverter.INSTANCE.dtoToVo(sysUserDto)));
    }

    @PutMapping
    public BaseResult<Integer> edit(@RequestBody @Valid SysUserDto sysUserDto){
        return BaseResult.ok(sysUserDomain.edit(SysUserConverter.INSTANCE.dtoToVo(sysUserDto)));
    }

    @DeleteMapping("/{id}")
    public BaseResult<Integer> delete(@PathVariable("id") Long id){
        return BaseResult.ok(sysUserDomain.delete(id));
    }

    @GetMapping("/{id}")
    public BaseResult<SysUserDto> select(@PathVariable("id") Long id){
        return BaseResult.ok(SysUserConverter.INSTANCE.voToDto(sysUserDomain.select(id)));
    }

    @PutMapping("/assignRole")
    public BaseResult<Integer> assignRole(@RequestBody SysUserDto sysUserDto){
        List<Long> roleIdList = sysUserDto.getSysRoleDtoList()
                .stream()
                .map(SysRoleDto::getId)
                .collect(Collectors.toList());
        return BaseResult.ok(sysUserDomain.assignRole(sysUserDto.getId(), roleIdList));
    }

    @GetMapping("/userCurrent")
    public BaseResult<SysUserDto> userCurrent(){
        return BaseResult.ok(UserContent.get());
    }

    @GetMapping("/selectRoleId")
    public BaseResult<List<Long>> selectRoleId(@RequestParam("userId") Long userId){
        return BaseResult.ok(sysUserDomain.selectRoleId(userId));
    }

    /**
     * 通过用户名称查询用户信息
     * @param userName 用户名
     * @return
     */
    @Override
    @GetMapping("/selectByUserName")
    public BaseResult<SysUserDto> selectByUserName(@RequestParam("userName") String userName) {
        SysUserVo sysUserVo = sysUserDomain.selectByUserName(userName);
        return BaseResult.ok(SysUserConverter.INSTANCE.voToDto(sysUserVo));
    }

    /**
     * 通过手机号码查询用户信息
     * @param mobile
     * @return
     */
    @Override
    @GetMapping("/selectByMobile")
    public BaseResult<SysUserDto> selectByMobile(@RequestParam("mobile") String mobile) {
        SysUserVo sysUserVo = sysUserDomain.selectByMobile(mobile);
        return BaseResult.ok(SysUserConverter.INSTANCE.voToDto(sysUserVo));
    }



}
