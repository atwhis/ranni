package com.ymchen.rannibase.entity.stock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_stock")
public class Stock implements Serializable {
    private static final long serialVersionUID = 999714042490659544L;

    @TableId(value ="id",type = IdType.AUTO)
    private Long id;

    @TableField("goods_no")
    private String goodsNo;

    @TableField("goods_name")
    private String goodsName;

    @TableField("stock")
    private Integer stock;
}
