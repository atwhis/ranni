package com.ymchen.ranni.component.datasource.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 自定义 p6spy sql输出格式
 *
 */
public class P6spySqlFormatConfigure implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StringUtils.isEmpty(sql) ? "" : LocalDateTime.now() + " | 耗时 " + elapsed + " ms | SQL 语句：\n" + sql;

    }
}
