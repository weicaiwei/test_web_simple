package com.caiwei.common.test.scopetest;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-05-04
 */
@Service
@Scope("prototype")
public class TestService {

    public void who() {
        System.out.println(this.toString());

    }
}
