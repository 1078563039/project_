package com.example.admin.pojo.entity.elstic;

import com.example.admin.pojo.entity.SysUserEntity;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "sysuser", type = "_doc", shards = 1, replicas = 1)
public class SysUserEntityElastic extends SysUserEntity {

}
