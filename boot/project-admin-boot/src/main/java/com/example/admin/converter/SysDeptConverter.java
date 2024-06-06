package com.example.admin.converter;

import com.example.admin.api.feign.dept.dto.SysDeptDto;
import com.example.admin.converter.base.MyConverter;
import com.example.admin.pojo.vo.SysDeptVo;
import com.example.admin.pojo.entity.SysDeptEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysDeptConverter extends MyConverter<SysDeptDto, SysDeptVo, SysDeptEntity> {

    SysDeptConverter INSTANCE = Mappers.getMapper(SysDeptConverter.class);

}
