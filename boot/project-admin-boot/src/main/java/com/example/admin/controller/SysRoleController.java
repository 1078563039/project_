package com.example.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.api.feign.role.SysRoleFeignApi;
import com.example.admin.api.feign.role.dto.SysRoleDto;
import com.example.admin.converter.SysRoleConverter;
import com.example.admin.domain.SysRoleDomain;
import com.example.admin.pojo.vo.SysRoleVo;
import com.example.common.base.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api(value = "前台用户管理", description = "前台用户管理 UserController")
@Slf4j
@RestController
@RequestMapping("/sysRole")
public class SysRoleController implements SysRoleFeignApi {

    @Autowired
    private SysRoleDomain sysRoleDomain;

    @GetMapping("/page")
    public BaseResult<Page<SysRoleDto>> page(@RequestParam(defaultValue = "1") Integer currentPage,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false, name = "roleName") String roleName){
        Page<SysRoleVo> page = sysRoleDomain.page(currentPage, pageSize, roleName);
        return BaseResult.ok(SysRoleConverter.INSTANCE.voToDtoPage(page));
    }

    @PostMapping
    public BaseResult<Integer> add(@RequestBody SysRoleDto sysRoleDto){
        return BaseResult.ok(sysRoleDomain.add(SysRoleConverter.INSTANCE.dtoToVo(sysRoleDto)));
    }

    @PutMapping
    public BaseResult<Integer> edit(@RequestBody SysRoleDto sysRoleDto){
        return BaseResult.ok(sysRoleDomain.edit(SysRoleConverter.INSTANCE.dtoToVo(sysRoleDto)));
    }

    @DeleteMapping("/{id}")
    public BaseResult<Integer> delete(@PathVariable("id") Long id){
        return BaseResult.ok(sysRoleDomain.delete(id));
    }

    @GetMapping("/{id}")
    public BaseResult<SysRoleDto> select(@PathVariable("id") Long id){
        return BaseResult.ok(SysRoleConverter.INSTANCE.voToDto(sysRoleDomain.select(id)));
    }

    @Override
    @GetMapping("/selectByUserId")
    public BaseResult<List<SysRoleDto>> selectByUserId(@RequestParam("userId") Long userId) {
        List<SysRoleVo> sysRoleVoList = sysRoleDomain.selectByUserId(userId);
        return BaseResult.ok(SysRoleConverter.INSTANCE.voToDtoList(sysRoleVoList));
    }
}

