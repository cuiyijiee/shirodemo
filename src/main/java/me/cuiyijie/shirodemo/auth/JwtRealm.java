package me.cuiyijie.shirodemo.auth;

import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.shirodemo.model.Account;
import me.cuiyijie.shirodemo.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: yjcui3
 * @Date: 2021/9/28 14:06
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("==========doGetAuthorizationInfo:获取用户权限");
        Subject subject = SecurityUtils.getSubject();
        Account account = (Account) subject.getPrincipal();

        Set<String> roles = new HashSet<>();
        roles.add(account.getRole());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.addStringPermission(account.getPerms());
        return info;
    }

    /**
     * 客户端传来的 username 和 password 会自动封装到 token，先根据 username 进行查询，
     * 如果返回 null，则表示用户名错误，直接 return null 即可，Shiro 会自动抛出 UnknownAccountException 异常。
     * <p>
     * 如果返回不为 null，则表示用户名正确，再验证密码，直接返回  SimpleAuthenticationInfo 对象即可，
     * 如果密码验证成功，Shiro 认证通过，
     * 否则返回 IncorrectCredentialsException 异常。
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("==========doGetAuthorizationInfo:获取用户认证");
        String jwtTokenStr = (String) authenticationToken.getPrincipal();
        if (jwtTokenStr == null) {
            throw new NullPointerException("X-TOKEN不能为空");
        }
        JwtUtil jwtUtil = new JwtUtil();
        if (!jwtUtil.isVerify(jwtTokenStr)) {
            throw new UnknownAccountException();
        }
        String username = (String) jwtUtil.decode(jwtTokenStr).get("username");
        log.info("在使用token登录" + username);
        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名
        return new SimpleAuthenticationInfo(jwtTokenStr, jwtTokenStr, "JwtRealm");
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
}
