package com.ymchen.ranniservice.stock.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_stock")
public class Stock implements Serializable {
    private static final long serialVersionUID = 999714042490659544L;

    @TableId("id")
    private Long id;

    @TableField("goods_no")
    private String goodsNo;

    @TableField("stock")
    private Integer stock;
}
