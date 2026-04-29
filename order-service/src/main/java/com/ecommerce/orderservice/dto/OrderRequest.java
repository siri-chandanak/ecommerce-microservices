package com.ecommerce.orderservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequest {
    private UUID productId;
    private int quantity;
}
