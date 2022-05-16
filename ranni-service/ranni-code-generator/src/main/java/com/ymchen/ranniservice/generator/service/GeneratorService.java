package com.ymchen.ranniservice.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymchen.rannibase.entity.base.PageRequest;
import com.ymchen.ranniservice.generator.entity.Column;
import com.ymchen.ranniservice.generator.entity.Table;

import java.util.List;

/**
 * @author MrBird
 */
public interface GeneratorService {

    /**
     * 获取数据库列表
     *
     * @param databaseType databaseType
     * @return 数据库列表
     */
    List<String> getDatabases(String databaseType);

    /**
     * 获取数据表
     *
     * @param tableName    tableName
     * @param request      request
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @return 数据表分页数据
     */
    IPage<Table> getTables(String tableName, PageRequest request, String databaseType, String schemaName);

    /**
     * 获取数据表列信息
     *
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @param tableName    tableName
     * @return 数据表列信息
     */
    List<Column> getColumns(String databaseType, String schemaName, String tableName);

}
