package com.ecommerce.orderservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderEvent
{
    private UUID orderId;
    private String status;
    private double amount;
}
