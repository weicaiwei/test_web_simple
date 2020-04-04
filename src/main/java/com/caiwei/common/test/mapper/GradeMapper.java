package com.caiwei.common.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: GradeMapper
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/11/1 14:09
 */
@Mapper
@Repository
public interface GradeMapper {

    @Select(" <script>" +
            " select * from grade" +
            " <where>" +
            " <if test='id !=null'> id = #{id}</if>" +
            " <if test='score !=null'> and score = #{score}</if>" +
            " </where>" +
            " </script>"
    )
    List<Map> queryGradeList(String id, String score);

    @Select("select * from student")
    List<Map> queryStudentList();

}
