package com.ymchen.ranniservice.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 2599951625831302420L;

    @TableId("id")
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
