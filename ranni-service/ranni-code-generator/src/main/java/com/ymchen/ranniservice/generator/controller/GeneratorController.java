package com.ymchen.ranniservice.generator.controller;


import com.ymchen.rannibase.constant.RanniApplicationConstant;
import com.ymchen.rannibase.entity.base.PageRequest;
import com.ymchen.rannibase.util.RanniUtil;
import com.ymchen.ranniservice.generator.entity.Column;
import com.ymchen.ranniservice.generator.entity.GeneratorConfig;
import com.ymchen.ranniservice.generator.helper.GeneratorHelper;
import com.ymchen.ranniservice.generator.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/generator")
public class GeneratorController {

    private static final String SUFFIX = "_code.zip";

    private final GeneratorService generatorService;
    private final GeneratorHelper generatorHelper;

    @GetMapping("test")
    public Object test() {
        return "test";
    }

    @GetMapping("getTables")
    public Object getTables(String tableName, String datasource, PageRequest request) {
        return generatorService.getTables(tableName, request, "mysql", datasource);
    }

    @PostMapping("generate")
    public void generate(@NotBlank(message = "{required}") String name,
                         @NotBlank(message = "{required}") String datasource, GeneratorConfig generatorConfig,
                         String remark, HttpServletResponse response) throws Exception {

        String className = name;
        if (GeneratorConfig.TRIM_YES.equals(generatorConfig.getIsTrim())) {
            className = RegExUtils.replaceFirst(name, generatorConfig.getTrimValue(), StringUtils.EMPTY);
        }

        generatorConfig.setTableName(name);
        generatorConfig.setClassName(RanniUtil.underscoreToCamel(className));
        generatorConfig.setTableComment(remark);

        // 删除临时目录
        FileSystemUtils.deleteRecursively(new File(RanniApplicationConstant.RANNI_GENERATOR_TEMP_PATH));

        // 生成代码到临时目录
        List<Column> columns = generatorService.getColumns("mysql", datasource, name);
        generatorHelper.generateEntityFile(columns, generatorConfig);
        generatorHelper.generateMapperFile(columns, generatorConfig);
        generatorHelper.generateMapperXmlFile(columns, generatorConfig);
        generatorHelper.generateServiceFile(columns, generatorConfig);
        generatorHelper.generateServiceImplFile(columns, generatorConfig);
        generatorHelper.generateControllerFile(columns, generatorConfig);

//        // 打包
//        String zipFile = System.currentTimeMillis() + SUFFIX;
//        FileUtil.compress(RanniApplicationConstant.RANNI_GENERATOR_TEMP_PATH + "src", zipFile);
//        // 下载
//        FileUtil.download(zipFile, name + SUFFIX, true, response);
//        // 删除临时目录
//        FileSystemUtils.deleteRecursively(new File(RanniApplicationConstant.RANNI_GENERATOR_TEMP_PATH ));
    }

}
