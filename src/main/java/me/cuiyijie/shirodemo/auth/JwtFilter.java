package me.cuiyijie.shirodemo.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: yjcui3
 * @Date: 2023/7/28 15:16
 */
@Slf4j
public class JwtFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        //这里先让它始终返回false来使用onAccessDenied()方法
        log.info("isAccessAllowed 方法被调用");
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.info("onAccessDenied 方法被调用");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwtTokenStr = httpServletRequest.getHeader("X-TOKEN");
        log.info("获取到请求头中的jwt token：{}", jwtTokenStr);
        JwtToken jwtToken = new JwtToken(jwtTokenStr);
        try {
            getSubject(servletRequest, servletResponse).login(jwtToken);
        } catch (Exception exception) {
            log.error("登录发生异常：", exception);
            onLoginFail(servletResponse);
            return false;
        }
        return true;
    }

    //登录失败时默认返回 401 状态码
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("登录失败");
    }
}
