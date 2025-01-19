package com.example.OrderService1.DTO;

import lombok.Data;

@Data
public class Inventory {
    private Long id;
    private Long productId;
    private Integer stockLevel;
    private String lastUpdated;
}
