package me.cuiyijie.shirodemo.service;

import me.cuiyijie.shirodemo.model.SysUser;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 13:48
 */
public interface SysUserService {

    SysUser findByUserName(String userName);

}
