package me.cuiyijie.shirodemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.cuiyijie.shirodemo.mapper.SysUserMapper;
import me.cuiyijie.shirodemo.model.SysUser;
import me.cuiyijie.shirodemo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 13:49
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return sysUserMapper.selectOne(wrapper);
    }
}
