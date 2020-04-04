package com.caiwei.common.test.controller;

import com.caiwei.common.test.mapper.GradeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: PageHelperController
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/11/1 14:07
 */
@CrossOrigin
@RestController
public class PageHelperController {

    @Autowired
    private GradeMapper gradeMapper;


    @GetMapping("/page")
    public PageInfo<Map> queryPageGrade(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Map> idList = gradeMapper.queryStudentList();
        return new PageInfo<>(idList);
    }
}
