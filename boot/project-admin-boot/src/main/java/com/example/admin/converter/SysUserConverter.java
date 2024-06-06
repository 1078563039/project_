package com.example.admin.converter;

import com.example.admin.api.feign.user.dto.SysUserDto;
import com.example.admin.converter.base.MyConverter;
import com.example.admin.pojo.entity.SysUserEntity;
import com.example.admin.pojo.vo.SysUserVo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * user转换工具类，通过default修饰进行自定义的转换实现
 */
@Mapper(uses = {SysDeptConverter.class})
public interface SysUserConverter extends MyConverter<SysUserDto, SysUserVo, SysUserEntity> {

    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

    @Mappings({
            @Mapping(source = "sysDeptEntity", target = "sysDeptVo"),
            @Mapping(source = "sysRoleEntityList", target = "sysRoleVoList")
    })
    SysUserVo entityToVo(SysUserEntity sysUserEntity);

    @Mappings({
            @Mapping(source = "sysDeptVo", target = "sysDeptDto"),
            @Mapping(source = "sysRoleVoList", target = "sysRoleDtoList")
    })
    SysUserDto voToDto(SysUserVo sysUserVo);

}
