package com.ecommerce.productservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="products")
@Data
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private double price;

    private int stock;

    private LocalDateTime createdAt;
}
