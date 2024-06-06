package com.example.common.mybatis.config;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.common.utils.content.UserContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;

//@Component
@Slf4j
public class MybatisMetaHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String userName = Objects.nonNull(UserContent.get())?UserContent.get().getUserName():null;
        Date now = new Date();
        log.info("start insert fill ....");
        metaObject.setValue("createBy", userName);
        metaObject.setValue("createTime", now);
        metaObject.setValue("updateBy", userName);
        metaObject.setValue("updateTime", now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userName = Objects.nonNull(UserContent.get())?UserContent.get().getUserName():null;
        Date now = new Date();
        log.info("start update fill ....");
        metaObject.setValue("updateBy", userName);
        metaObject.setValue("updateTime", now);
    }

}
