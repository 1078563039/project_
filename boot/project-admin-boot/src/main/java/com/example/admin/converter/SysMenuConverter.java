package com.example.admin.converter;

import com.example.admin.api.feign.menu.dto.SysMenuDto;
import com.example.admin.converter.base.MyConverter;
import com.example.admin.pojo.entity.SysMenuEntity;
import com.example.admin.pojo.vo.SysMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysMenuConverter extends MyConverter<SysMenuDto, SysMenuVo, SysMenuEntity> {

    SysMenuConverter INSTANCE = Mappers.getMapper(SysMenuConverter.class);

}
