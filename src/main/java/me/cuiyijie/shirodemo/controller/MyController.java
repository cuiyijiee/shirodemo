package me.cuiyijie.shirodemo.controller;

import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.shirodemo.auth.JwtUtil;
import me.cuiyijie.shirodemo.model.SysUser;
import me.cuiyijie.shirodemo.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: yjcui3
 * @Date: 2021/9/28 14:20
 */
@Slf4j
@RestController
public class MyController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("username") String username,
                                                     @RequestParam("password") String password) {
        log.info("username:{},password:{}", username, password);
        SysUser sysUser = sysUserService.findByUserName(username);
        Map<String, String> map = new HashMap<>();
        if (sysUser == null || !password.equals(sysUser.getPassword())) {
            map.put("msg", "用户不存在或密码错误!");
            return ResponseEntity.ok(map);
        }
        JwtUtil jwtUtil = new JwtUtil();
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("username", username);
        String jwtToken = jwtUtil.encode(username, 5 * 60 * 1000, chaim);
        map.put("msg", "登录成功");
        map.put("token", jwtToken);
        return ResponseEntity.ok(map);
    }

    @RequestMapping("/testdemo")
    public ResponseEntity<String> testdemo() {
        return ResponseEntity.ok("我爱蛋炒饭");
    }

    @RequiresPermissions("test")
    @RequestMapping("/testpermission")
    public ResponseEntity<String> testpermission() {
        return ResponseEntity.ok("我爱蛋炒饭带权限");
    }
}
