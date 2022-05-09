package com.ymchen.rannibase.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymchen.rannibase.entity.base.PageRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据库查询排序order by
 */
public abstract class MybatisPlusOrderUtil {


    private MybatisPlusOrderUtil() {

    }

    /**
     * 处理排序（分页情况下） for mybatis-plus
     *
     * @param request      PageRequest
     * @param page         Page
     * @param defaultSort  默认排序的字段
     * @param defaultOrder 默认排序规则
     */
    public static void handlePageSort(PageRequest request, Page<?> page, String defaultSort, String defaultOrder) {
        page.setCurrent(request.getPageNum());
        page.setSize(request.getPageSize());
        String sortField = request.getField();

        if (StringUtils.isNotBlank(request.getField())
                && StringUtils.isNotBlank(request.getOrder())
                && !StringUtils.equalsIgnoreCase(request.getField(), "null")
                && !StringUtils.equalsIgnoreCase(request.getOrder(), "null")) {
            if (StringUtils.equals(request.getOrder(), "desc")) {
                page.addOrder(OrderItem.desc(sortField));
            } else {
                page.addOrder(OrderItem.asc(sortField));
            }
        } else {
            if (StringUtils.isNotBlank(defaultSort)) {
                if (StringUtils.equals(defaultOrder, "desc")) {
                    page.addOrder(OrderItem.desc(defaultSort));
                } else {
                    page.addOrder(OrderItem.asc(defaultSort));
                }
            }
        }
    }
}
