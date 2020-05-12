package com.caiwei.common.test.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-05-04
 */
@Component
public class SpringBeanFactoryUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull  ApplicationContext applicationContext) throws BeansException {
        SpringBeanFactoryUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
