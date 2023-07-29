package me.cuiyijie.shirodemo.auth;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @Author: yjcui3
 * @Date: 2023/7/28 15:15
 */
public class JwtDefaultSubjectFactory extends DefaultWebSubjectFactory {
    /**
     * 不使用Session来记录用户信息，所以需要在这里进行关闭session
     *
     * @param context
     * @return
     */
    @Override
    public Subject createSubject(SubjectContext context) {
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
