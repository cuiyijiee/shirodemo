package me.cuiyijie.shirodemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.cuiyijie.shirodemo.mapper.SysUserMapper;
import me.cuiyijie.shirodemo.model.SysUser;
import me.cuiyijie.shirodemo.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 13:49
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser findByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return baseMapper.selectOne(wrapper);
    }
}
