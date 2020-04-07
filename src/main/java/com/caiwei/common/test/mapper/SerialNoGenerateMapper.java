package com.caiwei.common.test.mapper;

import com.caiwei.common.test.module.SerialNoGenerate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 流水号生成mapper
 *
 * @auther caiwei
 * @date 2020-04-08
 */
@Mapper
@Repository
public interface SerialNoGenerateMapper {


    @Select(" select app_id appId, now_serial_no nowSerialNo, update_time updateTime" +
            " from serial_no_generate" +
            " where app_id = #{appId}")
    SerialNoGenerate queryNowSerialNo(String appId);

    @Update(" update serial_no_generate" +
            " set now_serial_no = #{nowSerialNo}," +
            "     update_time = #{updateTime}" +
            " where " +
            "  app_id = #{appId} and " +
            "  ( now_serial_no != #{nowSerialNo} or " +
            "    now_serial_no is null )")
    int updateSerialNo(SerialNoGenerate serialNoGenerate);


}
