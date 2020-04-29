package com.caiwei.common.test.mapper;

import com.caiwei.common.test.module.SerialNoGenerate;
import com.caiwei.common.test.module.UserOnline;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-09
 */
@Mapper
@Repository
public interface UserOnlineMapper {

    @Select(" select user_id userId, session_id sessionId, login_time loginTime" +
            " from user_online" +
            " where user_id = #{userId}")
    UserOnline queryUserOnline(String userId);

    @Insert(" insert into user_online(user_id, session_id, login_time)" +
            " values( #{userId},  #{sessionId}, #{loginTime} )")
    int addUserOnline(UserOnline userOnline);

    @Update(" update serial_no_generate" +
            " set now_serial_no = #{nowSerialNo}," +
            "     update_time = #{updateTime}" +
            " where " +
            "  app_id = #{appId} and " +
            "  ( now_serial_no != #{nowSerialNo} or " +
            "    now_serial_no is null )")
    int updateUserOnline(UserOnline userOnline);
}
