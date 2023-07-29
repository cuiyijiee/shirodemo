package me.cuiyijie.shirodemo.service;

import me.cuiyijie.shirodemo.model.Account;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 13:48
 */
public interface AccountService{

    Account findByUserName(String userName);

}
