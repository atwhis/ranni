package com.ymchen.ranniservice.generator.mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymchen.ranniservice.generator.entity.Column;
import com.ymchen.ranniservice.generator.entity.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GeneratorMapper {

    /**
     * 获取数据列表
     *
     * @param databaseType databaseType
     * @return 数据库列表
     */
    List<String> getDatabases(@Param("databaseType") String databaseType);

    /**
     * 获取数据表
     *
     * @param page         page
     * @param tableName    tableName
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @param <T>          Type
     * @return 数据表分页数据
     */
    <T> IPage<Table> getTables(Page<T> page, @Param("tableName") String tableName, @Param("databaseType") String databaseType, @Param("schemaName") String schemaName);

    /**
     * 获取数据表列信息
     *
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @param tableName    tableName
     * @return 数据表列信息
     */
    List<Column> getColumns(@Param("databaseType") String databaseType, @Param("schemaName") String schemaName, @Param("tableName") String tableName);
}
