package me.cuiyijie.shirodemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.cuiyijie.shirodemo.mapper.AccountMapper;
import me.cuiyijie.shirodemo.model.Account;
import me.cuiyijie.shirodemo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 13:49
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account findByUserName(String username) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return accountMapper.selectOne(wrapper);
    }
}
