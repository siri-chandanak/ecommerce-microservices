package com.ecommerce.productservice.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String description;
    private double price;
    private int stock;
}
