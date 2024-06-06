package demo;

import com.example.admin.ProjectAdminApplication;
import com.example.admin.dao.SysUserDao;
import com.example.admin.pojo.entity.SysUserEntity;
import com.example.admin.service.SysUserService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectAdminApplication.class)
public class DemoTest {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserDao sysUserDao;

    @Test
    public void test() {
        sysUserService.list();
    }

    @Test
    public void batchUpdateUser(){
        ArrayList<SysUserEntity> sysUserEntityArrayList = new ArrayList<>();
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(1L);
        sysUserEntity.setUserName("admin");
        sysUserEntity.setNickName("管理员");
        sysUserEntity.setMobile("12345678901");
        sysUserEntity.setEmail("");
        sysUserEntity.setSex(1);
        sysUserEntityArrayList.add(sysUserEntity);
        sysUserEntityArrayList.add(sysUserEntity);
        sysUserEntityArrayList.add(sysUserEntity);
        sysUserDao.updateById(sysUserEntity);
        sysUserDao.updateBatch(sysUserEntityArrayList);
    }


}

