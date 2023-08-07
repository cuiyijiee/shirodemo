package me.cuiyijie.shirodemo.auth;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.shirodemo.constants.SysConstants;
import me.cuiyijie.shirodemo.utils.HttpContextUtils;
import me.cuiyijie.shirodemo.utils.R;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

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
public class JwtFilter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String token = ((HttpServletRequest) servletRequest).getHeader(SysConstants.AUTH_HEADER_KEY);
        return new JwtToken(token);
    }

    /**
     * 判断用户是否已经登录，如果是options的请求则放行，否则进行调用onAccessDenied进行token认证流程
     *
     * @param servletRequest
     * @param servletResponse
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        //这里先让它始终返回false来使用onAccessDenied()方法
        log.info("isAccessAllowed 方法被调用");
        return ((HttpServletRequest) servletRequest).getMethod().equals(RequestMethod.OPTIONS.name());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.info("onAccessDenied 方法被调用");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwtTokenStr = httpServletRequest.getHeader(SysConstants.AUTH_HEADER_KEY);
        if (StringUtils.isBlank(jwtTokenStr)) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(R.error("invalid token"));
            httpResponse.getWriter().print(json);
            return false;
        }
        log.info("获取到请求头中的jwt token：{}", jwtTokenStr);
        JwtToken jwtToken = new JwtToken(jwtTokenStr);
        try {
            getSubject(servletRequest, servletResponse).login(jwtToken);
        } catch (Exception exception) {
            log.error("登录发生异常：", exception);
            onLoginFail(servletResponse);
            return false;
        }
        return executeLogin(servletRequest, servletResponse);
    }

    //登录失败时默认返回 401 状态码
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("登录失败");
    }
}
