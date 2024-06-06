package com.example.admin.dao.elastic;

import com.example.admin.pojo.entity.elstic.SysUserEntityElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserElasticDao extends ElasticsearchRepository<SysUserEntityElastic, Integer> {

}
