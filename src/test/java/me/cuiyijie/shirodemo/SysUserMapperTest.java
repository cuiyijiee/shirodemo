package me.cuiyijie.shirodemo;

import me.cuiyijie.shirodemo.model.SysUser;
import me.cuiyijie.shirodemo.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 11:35
 */
@SpringBootTest
class SysUserMapperTest {

    @Autowired
    private SysUserService accountService;

    @Test
    void test(){
        SysUser sysUser = accountService.findByUserName("ww");
        System.out.println(sysUser);
    }
}
