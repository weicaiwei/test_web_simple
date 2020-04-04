package com.caiwei.common.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-01
 */
@Data
@PropertySource(value = "classpath:config/config.properties", encoding = "utf-8")
@ConfigurationProperties("custom.caiwei")
@Component
public class Caiwei {
    private String name;
    private Integer age;
    private String love;
}
