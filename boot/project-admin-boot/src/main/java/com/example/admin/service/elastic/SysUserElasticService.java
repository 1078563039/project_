package com.example.admin.service.elastic;

import com.example.admin.pojo.entity.elstic.SysUserEntityElastic;
import org.springframework.data.domain.Page;

public interface SysUserElasticService {
    Page<SysUserEntityElastic> searchUsers(String query, int page, int size);

//    void save(SysUserEntityElastic sysUserEntityElastic);

}
