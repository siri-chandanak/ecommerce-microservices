package com.ecommerce.orderservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Data
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID productId;

    private int quantity;

    private double totalPrice;

    private String status; // {created or failed}

    private LocalDateTime createdAt;
}
