package com.ymchen.rannibase.dto.order;

import com.ymchen.rannibase.entity.order.Order;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDTO extends Order implements Serializable {
    private static final long serialVersionUID = 2443569022717057784L;

    public String userName;

    public String goodsName;

}
