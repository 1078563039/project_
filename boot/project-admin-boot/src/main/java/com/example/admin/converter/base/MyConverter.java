package com.example.admin.converter.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.base.entity.BaseEntity;

import java.util.List;

public interface MyConverter<D, V, E extends BaseEntity> {

    V entityToVo(E e);

    List<V> entityToVoList(List<E> eList);

    E voToEntity(V d);

    List<E> voToEntityList(List<V> dList);

    V dtoToVo(D d);

    List<V> dtoToVoList(List<D> dList);

    D voToDto(V v);

    List<D> voToDtoList(List<V> vList);

    Page<D> voToDtoPage(Page<V> vPage);

    Page<V> entityToVoPage(Page<E> ePage);

}
