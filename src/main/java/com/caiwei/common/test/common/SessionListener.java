package com.caiwei.common.test.common;

import com.caiwei.common.test.mapper.UserOnlineMapper;
import com.caiwei.common.test.module.UserOnline;
import com.caiwei.common.test.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Objects;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-08
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private UserOnlineMapper userOnlineMapper;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        HttpSession session = httpSessionEvent.getSession();
        String sessionId = session.getId();
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        if (springContext != null) {
            springContext.getBean(SMSService.class);
        }
        String userId = (String) session.getAttribute("userId");
        UserOnline userOnline = userOnlineMapper.queryUserOnline(userId);
        //第一次登陆
        if (Objects.isNull(userOnline)) {
            userOnline = new UserOnline();
            userOnline.setUserId(userId);
            userOnline.setSessionId(sessionId);
            userOnline.setLoginTime(Utils.now());
            userOnlineMapper.addUserOnline(userOnline);
        }else {//不是第一次登陆
            String oldSessionId = userOnline.getSessionId();
            HttpSession oldSession = sessionContext.getSession(oldSessionId);
            if (Objects.nonNull(oldSession)) {//如果之前的session还未失效，那么手动注销session,并删除map中的session
                oldSession.invalidate();
                sessionContext.removeSession(oldSession);
            }
            sessionContext.addSession(session);
            userOnline.setSessionId(sessionId);
            userOnline.setLoginTime(Utils.now());
            userOnlineMapper.updateUserOnline(userOnline);

        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        sessionContext.removeSession(httpSessionEvent.getSession());
    }
}
