package com.ymchen.rannibase.dto.stock;

import com.ymchen.rannibase.entity.stock.Stock;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockDTO extends Stock implements Serializable {
    private static final long serialVersionUID = 8057382496114277460L;
}
