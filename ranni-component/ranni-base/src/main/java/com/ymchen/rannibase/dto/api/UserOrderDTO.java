package com.ymchen.rannibase.dto.api;

import com.ymchen.rannibase.dto.order.OrderDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserOrderDTO implements Serializable {
    private static final long serialVersionUID = 583127094177254920L;

    private String userName;

    private Long userId;

    private List<OrderDTO> orders;
}
