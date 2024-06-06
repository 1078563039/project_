package com.example.admin.converter;

import com.example.admin.api.feign.role.dto.SysRoleDto;
import com.example.admin.converter.base.MyConverter;
import com.example.admin.pojo.entity.SysRoleEntity;
import com.example.admin.pojo.vo.SysRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * user转换工具类，通过default修饰进行自定义的转换实现
 */
@Mapper
public interface SysRoleConverter extends MyConverter<SysRoleDto, SysRoleVo, SysRoleEntity> {

    SysRoleConverter INSTANCE = Mappers.getMapper(SysRoleConverter.class);

}
