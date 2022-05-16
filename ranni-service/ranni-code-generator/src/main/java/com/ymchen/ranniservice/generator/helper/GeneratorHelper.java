package com.ymchen.ranniservice.generator.helper;

import com.google.common.io.Files;
import com.ymchen.rannibase.constant.RanniApplicationConstant;
import com.ymchen.rannibase.util.GsonUtils;
import com.ymchen.rannibase.util.RanniUtil;
import com.ymchen.ranniservice.generator.entity.Column;
import com.ymchen.ranniservice.generator.entity.GeneratorConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 代码生成器工具类
 *
 * @author MrBird
 */
@Slf4j
@Component
public class GeneratorHelper {

    private static String getFilePath(GeneratorConfig configure, String packagePath, String suffix) {
        String filePath = RanniApplicationConstant.RANNI_GENERATOR_TEMP_PATH + configure.getJavaPath() +
                packageConvertPath(configure.getBasePackage() + "." + packagePath);
        filePath += configure.getClassName() + suffix;
        return filePath;
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    public void generateEntityFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = ".java";
        String path = getFilePath(configure, configure.getEntityPackage(), suffix);
        String templateName = "entity.ftl";
        File entityFile = new File(path);
        Map data = toJsonObject(configure);
        data.put("hasDate", false);
        data.put("hasBigDecimal", false);
        columns.forEach(c -> {
            c.setField(RanniUtil.underscoreToCamel(StringUtils.lowerCase(c.getName())));
            if (StringUtils.containsAny(c.getType(), "date", "datetime", "timestamp")) {
                data.put("hasDate", true);
            }
            if (StringUtils.containsAny(c.getType(), "decimal", "numeric")) {
                data.put("hasBigDecimal", true);
            }
        });
        data.put("columns", columns);
        this.generateFileByTemplate(templateName, entityFile, data);
    }

    public void generateMapperFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = "Mapper.java";
        String path = getFilePath(configure, configure.getMapperPackage(), suffix);
        String templateName = "mapper.ftl";
        File mapperFile = new File(path);
        generateFileByTemplate(templateName, mapperFile, toJsonObject(configure));
    }

    public void generateServiceFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = "Service.java";
        String path = getFilePath(configure, configure.getServicePackage(), suffix);
        String templateName = "service.ftl";
        File serviceFile = new File(path);
        generateFileByTemplate(templateName, serviceFile, toJsonObject(configure));
    }

    public void generateServiceImplFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = "ServiceImpl.java";
        String path = getFilePath(configure, configure.getServiceImplPackage(), suffix);
        String templateName = "serviceImpl.ftl";
        File serviceImplFile = new File(path);
        generateFileByTemplate(templateName, serviceImplFile, toJsonObject(configure));
    }

    public void generateControllerFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = "Controller.java";
        String path = getFilePath(configure, configure.getControllerPackage(), suffix);
        String templateName = "controller.ftl";
        File controllerFile = new File(path);
        generateFileByTemplate(templateName, controllerFile, toJsonObject(configure));
    }

    public void generateMapperXmlFile(List<Column> columns, GeneratorConfig configure) throws Exception {
        String suffix = "Mapper.xml";
        String path = getFilePath(configure, configure.getMapperXmlPackage(), suffix);
        String templateName = "mapperXml.ftl";
        File mapperXmlFile = new File(path);
        Map data = toJsonObject(configure);
        columns.forEach(c -> c.setField(RanniUtil.underscoreToCamel(StringUtils.lowerCase(c.getName()))));
        data.put("columns", columns);
        generateFileByTemplate(templateName, mapperXmlFile, data);
    }

    @SuppressWarnings("all")
    private void generateFileByTemplate(String templateName, File file, Object data) throws Exception {
        Template template = getTemplate(templateName);
        Files.createParentDirs(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8), 10240)) {
            template.process(data, out);
        } catch (Exception e) {
            String message = "代码生成异常";
            log.error(message, e);
            throw new Exception(message);
        }
    }

    private Map toJsonObject(Object o) {
        return GsonUtils.getInstance().fromJson(GsonUtils.getInstance().toJson(o), Map.class);
    }

    private Template getTemplate(String templateName) throws Exception {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        String templatePath = GeneratorHelper.class.getResource("/templates/").getPath();
        File file = new File(templatePath);
        if (!file.exists()) {
            templatePath = System.getProperties().getProperty("java.io.tmpdir");
            file = new File(templatePath + File.separator + templateName);
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(GeneratorHelper.class.getClassLoader().getResourceAsStream("classpath:/templates/" + templateName)), file);
        }
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        configuration.setDefaultEncoding("utf-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return configuration.getTemplate(templateName);

    }
}
