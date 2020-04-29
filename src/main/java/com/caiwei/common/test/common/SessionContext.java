package com.caiwei.common.test.common;

import org.apache.catalina.Session;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-09
 */
@Component
public class SessionContext {

    private Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    public HttpSession getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    public void addSession(HttpSession session) {
        sessionMap.put(session.getId(), session);
    }

    public void removeSession(HttpSession session) {
        sessionMap.remove(session.getId());
    }


}
