package me.cuiyijie.shirodemo;

import me.cuiyijie.shirodemo.model.Account;
import me.cuiyijie.shirodemo.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 11:35
 */
@SpringBootTest
class AccountMapperTest {

    @Autowired
    private AccountService accountService;

    @Test
    void test(){
        Account account = accountService.findByUserName("ww");
        System.out.println(account);
    }
}
