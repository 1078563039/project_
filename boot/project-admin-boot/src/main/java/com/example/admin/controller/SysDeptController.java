package com.example.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.api.feign.dept.SysDeptFeignApi;
import com.example.admin.api.feign.dept.dto.SysDeptDto;
import com.example.admin.converter.SysDeptConverter;
import com.example.admin.domain.SysDeptDomain;
import com.example.admin.pojo.vo.SysDeptVo;
import com.example.common.base.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/sysDept")
public class SysDeptController implements SysDeptFeignApi {

    @Autowired
    private SysDeptDomain sysDeptDomain;

    @GetMapping("/tree")
    public BaseResult<List<SysDeptDto>> tree(@RequestParam(required = false) Long pid){
        List<SysDeptVo> sysDeptDtoList = sysDeptDomain.tree(Objects.isNull(pid) ? 0L : pid);
        return BaseResult.ok(SysDeptConverter.INSTANCE.voToDtoList(sysDeptDtoList));
    }

    @PostMapping
    public BaseResult<Integer> add(@Valid @RequestBody SysDeptDto sysDeptDto){
        return BaseResult.ok(sysDeptDomain.add(SysDeptConverter.INSTANCE.dtoToVo(sysDeptDto)));
    }

    @PutMapping
    public BaseResult<Integer> edit(@Valid @RequestBody SysDeptDto sysDeptDto){
        return BaseResult.ok(sysDeptDomain.edit(SysDeptConverter.INSTANCE.dtoToVo(sysDeptDto)));
    }

    @DeleteMapping("/{id}")
    public BaseResult<Integer> delete(@PathVariable Long id){
        return BaseResult.ok(sysDeptDomain.delete(id));
    }

    @GetMapping("/{id}")
    public BaseResult<SysDeptDto> select(@PathVariable Long id){
        return BaseResult.ok(SysDeptConverter.INSTANCE.voToDto(sysDeptDomain.select(id)));
    }

}
