package com.ymchen.ranniservice.generator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymchen.rannibase.entity.base.PageRequest;
import com.ymchen.rannibase.util.MybatisPlusOrderUtil;
import com.ymchen.ranniservice.generator.entity.Column;
import com.ymchen.ranniservice.generator.entity.Table;
import com.ymchen.ranniservice.generator.mapper.GeneratorMapper;
import com.ymchen.ranniservice.generator.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final GeneratorMapper generatorMapper;

    @Override
    public List<String> getDatabases(String databaseType) {
        return generatorMapper.getDatabases(databaseType);
    }

    @Override
    public IPage<Table> getTables(String tableName, PageRequest request, String databaseType, String schemaName) {
        Page<Table> page = new Page<>(request.getPageNum(), request.getPageSize());
        MybatisPlusOrderUtil.handlePageSort(request, page, "null", "null");
        return generatorMapper.getTables(page, tableName, databaseType, schemaName);
    }

    @Override
    public List<Column> getColumns(String databaseType, String schemaName, String tableName) {
        return generatorMapper.getColumns(databaseType, schemaName, tableName);
    }
}
