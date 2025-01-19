package com.example.OrderService1.DTO;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private Boolean availability;
}
