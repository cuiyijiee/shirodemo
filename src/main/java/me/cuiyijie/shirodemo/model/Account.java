package me.cuiyijie.shirodemo.model;

import lombok.Data;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 11:35
 */
@Data
public class Account {
    private Integer id;
    private String username;
    private String password;
    private String perms;
    private String role;
}