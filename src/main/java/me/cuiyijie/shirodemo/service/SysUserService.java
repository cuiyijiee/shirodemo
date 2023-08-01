package me.cuiyijie.shirodemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.cuiyijie.shirodemo.model.SysUser;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 13:48
 */
public interface SysUserService extends IService<SysUser> {

    SysUser findByUserName(String userName);

}
