package com.ymchen.ranniservice.api.controller;

import com.ymchen.ranni.component.elastic.util.ESUtil;
import com.ymchen.rannibase.entity.base.RanniResult;
import com.ymchen.rannibase.util.GsonUtils;
import com.ymchen.ranniservice.api.dao.StudentESDao;
import com.ymchen.ranniservice.api.entity.Student;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentESDao studentESDao;
    private final ESUtil esUtil;

    @ApiOperation("新增学生索引")
    @PostMapping("add")
    public Object addStudent(Student student) {

        studentESDao.save(student);

        return RanniResult.SUCCESS();
    }

    @ApiOperation("新增索引数据")
    @PostMapping("addIndexData")
    public Object addIndexData(Student student, String index) {
        esUtil.insertData(index, GsonUtils.getInstance().toJson(student));
        return RanniResult.SUCCESS();
    }

    @ApiOperation("查询索引数据")
    @GetMapping("queryIndexData")
    public Object queryIndexData(String index, String field, String value) {
        return esUtil.queryIndexData(index, field, value);
    }

    @ApiOperation("查询学生索引")
    @GetMapping("get")
    public Object getStudent(String id) {
        return studentESDao.findById(id);
    }


}
