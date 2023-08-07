package me.cuiyijie.shirodemo.auth;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import me.cuiyijie.shirodemo.constants.SysConstants;
import me.cuiyijie.shirodemo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(SysConstants.AUTH_HEADER_KEY);
        //凭证为空
        if (StringUtils.isBlank(token)) {
        }
        Claims claims = jwtUtil.decode(token);
        if (claims == null || jwtUtil.isTokenExpired(claims.getExpiration())) {
        }
        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(SysConstants.AUTH_LOGIN_USER_ID, claims.getSubject());
        return true;
    }

}
