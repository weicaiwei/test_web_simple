package com.caiwei.common.test.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: LoginMapper
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/11/18 09:25
 */
@Mapper
@Repository
public interface LoginMapper {

    @Select("select login_status from login where user_id =#{userId}")
    String queryLoginStatus(@Param("userId") String userId);

    @Insert("insert into login(user_id,login_status) values(#{userId},#{loginStatus})")
    void addLoginStatus(@Param("userId") String userId, @Param("loginStatus")String loginStatus);

    @Update("update login set login_status = #{loginStatus} where user_id = #{userId}")
    void updateLoginStatus(@Param("userId") String userId, @Param("loginStatus")String loginStatus);
}
