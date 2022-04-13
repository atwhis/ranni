package com.ymchen.rannibase.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 2599951625831302420L;

    @TableId(value ="id",type = IdType.AUTO)
    private Long id;

    @TableField("order_no")
    private String orderNo;

    @TableField("order_user")
    private Long orderUser;

    @TableField("order_state")
    private Integer orderState;

    @TableField("remark")
    private String remark;
}
