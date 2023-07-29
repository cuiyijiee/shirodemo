package me.cuiyijie.shirodemo.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: yjcui3
 * @Date: 2023/7/28 15:20
 */
public class JwtToken implements AuthenticationToken {

    private String jwtToken;

    public JwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }


    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
