package com.caiwei.common.test.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: LoginDao
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/11/18 09:40
 */
@Component
@Slf4j
public class LoginDao {

    @Autowired
    private LoginMapper loginMapper;

    public boolean isLogin(String userId) {
        String loginStatus = loginMapper.queryLoginStatus(userId);
        if (null == loginStatus || "0".equals(loginStatus)) {
            return false;
        } else if ("1".equals(loginStatus)) {
            return true;
        } else {
            log.error("login status exception;loginStatus=" + loginStatus);
            return false;
        }
    }

    public void login(String userId) {
        String loginStatus = loginMapper.queryLoginStatus(userId);
        if (null == loginStatus) {
            loginMapper.addLoginStatus(userId,"1");
        } else if ("0".equals(loginStatus)) {
            loginMapper.updateLoginStatus(userId, "1");
        }else {
            log.error("error login; userId=" + userId);
        }
    }

    public void logout(String userId) {
        String loginStatus = loginMapper.queryLoginStatus(userId);
        if ("1".equals(loginStatus)) {
            loginMapper.updateLoginStatus(userId, "0");
        } else {
            log.error("error logout; userId=" + userId);
        }
    }
}
