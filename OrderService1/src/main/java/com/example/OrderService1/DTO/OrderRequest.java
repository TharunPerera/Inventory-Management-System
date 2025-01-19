package com.example.OrderService1.DTO;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class OrderRequest {
    @NotNull
    private Long productId;

    @NotNull
    private Long customerId;

    @NotNull
    private Integer quantity;
}
