package com.example.admin.controller;

import com.example.admin.api.feign.menu.SysMenuFeignApi;
import com.example.admin.api.feign.menu.dto.SysMenuAssignSaveDto;
import com.example.admin.api.feign.menu.dto.SysMenuDto;
import com.example.admin.api.feign.user.dto.SysUserDto;
import com.example.admin.converter.SysMenuConverter;
import com.example.admin.domain.SysMenuDomain;
import com.example.admin.pojo.vo.SysMenuVo;
import com.example.common.base.result.BaseResult;
import com.example.common.utils.content.UserContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author dzk
 * @date Created in 2020/5/22 0:35
 */
@Slf4j
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController implements SysMenuFeignApi {

    @Autowired
    private SysMenuDomain sysMenuDomain;

    @Override
    public BaseResult<SysMenuDto> selectMenuList() {
        return null;
    }

    @Override
    public BaseResult<SysMenuDto> selectMenuRouter() {
        return null;
    }

    @GetMapping("/tree")
    public BaseResult<List<SysMenuDto>> tree(@RequestParam(required = false) Long pid){
        List<SysMenuVo> sysMenuDtoList = sysMenuDomain.tree(Objects.isNull(pid) ? 0L : pid);
        return BaseResult.ok(SysMenuConverter.INSTANCE.voToDtoList(sysMenuDtoList));
    }

    @PostMapping
    public BaseResult<Integer> add(@RequestBody SysMenuDto sysMenuDto){
        SysMenuVo sysMenuVo = SysMenuConverter.INSTANCE.dtoToVo(sysMenuDto);
        return BaseResult.ok(sysMenuDomain.add(sysMenuVo));
    }

    @PutMapping
    public BaseResult<Integer> edit(@RequestBody SysMenuDto sysMenuDto){
        SysMenuVo sysMenuVo = SysMenuConverter.INSTANCE.dtoToVo(sysMenuDto);
        return BaseResult.ok(sysMenuDomain.edit(sysMenuVo));
    }

    @DeleteMapping("/{id}")
    public BaseResult<Integer> delete(@PathVariable Long id){
        return BaseResult.ok(sysMenuDomain.delete(id));
    }

    @GetMapping("/{id}")
    public BaseResult<SysMenuDto> select(@PathVariable Long id){
        return BaseResult.ok(SysMenuConverter.INSTANCE.voToDto(sysMenuDomain.select(id)));
    }

    @GetMapping("/assignList/{roleId}")
    public BaseResult<List<SysMenuDto>> assignList(@PathVariable Long roleId){
        List<SysMenuVo> sysMenuVoList = sysMenuDomain.assignList(roleId);
        return BaseResult.ok(SysMenuConverter.INSTANCE.voToDtoList(sysMenuVoList));
    }

    @PostMapping("/assignSave")
    public BaseResult<Integer> assignSave(@RequestBody SysMenuAssignSaveDto sysMenuAssignSaveDto){
        List<Long> menuIdList = sysMenuAssignSaveDto.getMenuIdList();
        Long roleId = sysMenuAssignSaveDto.getRoleId();
        return BaseResult.ok(sysMenuDomain.assignSave(roleId, menuIdList));
    }

    //获取菜单权限列表
    @GetMapping("/routes")
    public BaseResult<List<SysMenuDto>> routes(){
        SysUserDto sysUserDto = UserContent.get();
        if (Objects.isNull(sysUserDto)){
            return BaseResult.ok();
        }
        // 通过用户id获取用户拥有的菜单集合
        List<SysMenuVo> sysMenuVoList = sysMenuDomain.routes(sysUserDto.getId());
        return BaseResult.ok(SysMenuConverter.INSTANCE.voToDtoList(sysMenuVoList));
    }

    @GetMapping("/permissions")
    public BaseResult<List<SysMenuDto>> permissions(){
        SysUserDto sysUserDto = UserContent.get();
        if (Objects.isNull(sysUserDto)){
            return BaseResult.ok();
        }
        // 通过用户id获取用户拥有的权限集合
        List<SysMenuVo> sysMenuDtoList = sysMenuDomain.permissions(sysUserDto.getId());
        return BaseResult.ok(SysMenuConverter.INSTANCE.voToDtoList(sysMenuDtoList));
    }
    
}
