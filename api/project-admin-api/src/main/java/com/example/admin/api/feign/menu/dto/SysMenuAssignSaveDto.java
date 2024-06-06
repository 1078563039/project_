package com.example.admin.api.feign.menu.dto;

import lombok.Data;

import java.util.List;

@Data
public class SysMenuAssignSaveDto {

    private Long roleId;

    private List<Long> menuIdList;

}
