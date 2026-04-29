package com.ecommerce.orderservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class Product {
    private UUID id;
    private String name;
    private double price;
    private int stock;
}