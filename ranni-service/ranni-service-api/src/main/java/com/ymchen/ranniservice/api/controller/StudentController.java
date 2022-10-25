package com.ymchen.ranniservice.api.controller;

import com.ymchen.ranni.component.elastic.util.ESUtil;
import com.ymchen.rannibase.entity.base.RanniResult;
import com.ymchen.rannibase.util.GsonUtils;
import com.ymchen.ranniservice.api.dao.StudentESDao;
import com.ymchen.ranniservice.api.entity.Student;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("student")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentESDao studentESDao;
    private final ESUtil esUtil;


    @ApiOperation("参数校验测试1")
    @PostMapping("validTest1")
    public Object validTest1(@Validated Student student) {
        return student;
    }

    @ApiOperation("参数校验测试2")
    @PostMapping("validTest2")
    public Object validTest2(@Validated Student student, @RequestParam(value = "newAddress", required = false) @NotBlank(message = "新地址不能为空") String newAddress) {
        student.setAddress(newAddress);
        return student;
    }

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


    /**
     * mustache 查询
     *
     * @param student
     * @return
     */
    @ApiOperation("模版查询")
    @GetMapping("mustacheQuery")
    public Object mustacheQuery(Student student) throws IOException {
        String scriptPath = "mustache/student.mustache";
        return esUtil.mustacheSearch(scriptPath, student);
    }
}
