package com.caiwei.common.test.controller;

import com.alibaba.fastjson.JSON;
import com.caiwei.common.test.mapper.LoginDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SessionController
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/11/15 14:27
 */

@RestController
@RequestMapping("session")
@Slf4j
public class SessionController {


    public SessionController() throws Exception {
        System.out.println("session Controller be created");


    }

    @Autowired
    private LoginDao loginDao;

    @GetMapping("remove")
    public void removeSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();

    }

    @GetMapping("not")
    public String noSession() {
        log.error("test exception", new NullPointerException());
        return "hello";
    }

    @GetMapping("get")
    public void getSession(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("before getSession,sessionId:" + request.getRequestedSessionId());
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        System.out.println("after getSession,sessionId:" + session.getId());

        try {
            response.getWriter().write(JSON.toJSONString(sessionId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("login")
    public Map login(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Map<String, String> responseMap = new HashMap<>();
        String userId = params.get("userId");
        HttpSession session = request.getSession();
        if (!loginDao.isLogin(userId)) {
            loginDao.login(userId);
            session.setAttribute("login", true);
            responseMap.put("message", "用户登录成功");
            return responseMap;
        } else {
            responseMap.put("message", "用户已登录");
            return responseMap;
        }

    }

    @GetMapping("logout")
    public Map logout(HttpServletRequest request,String userId) {
        loginDao.logout(userId);
        HttpSession session = request.getSession();
        session.invalidate();
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "用户登出成功");
        return responseMap;
    }
}
