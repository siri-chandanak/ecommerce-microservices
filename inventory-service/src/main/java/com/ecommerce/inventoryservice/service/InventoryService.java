package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public void reduceStock(UUID productId, int quantity)
    {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if(inventory.getAvailableStock() < quantity)
        {
            throw new RuntimeException("Not enough stock");
        }

        inventory.setAvailableStock(inventory.getAvailableStock() - quantity);
        inventoryRepository.save(inventory);
    }
}
