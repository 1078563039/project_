package com.example.admin.api.feign.dept;

import com.example.admin.api.feign.dept.fallback.SysDeptFeignApiFallBack;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Api(tags = "部门")
@Component
@FeignClient(value = "project-dept", contextId = "dept", path = "/admin/sysdept", fallback = SysDeptFeignApiFallBack.class)
public interface SysDeptFeignApi {


}
