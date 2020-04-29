package com.caiwei.common.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 公共配置表映射
 *
 * @auther caiwei
 * @date 2020-04-29
 */
@Mapper
@Repository
public interface CommonConfigMapper {

    @Select("select property_value from common_config where property_name = #{propertyName}")
    String getProperty(String propertyName);
}
