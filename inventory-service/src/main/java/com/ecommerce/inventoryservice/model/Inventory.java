package com.ecommerce.inventoryservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="inventory")
@Data
public class Inventory {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID productId;

    private int availableStock;
}
