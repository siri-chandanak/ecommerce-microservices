package com.ecommerce.inventoryservice.controller;


import com.ecommerce.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/reduce")
    public String reduce(@RequestParam UUID productId, @RequestParam int quantity)
    {
        inventoryService.reduceStock(productId,quantity);
        return "Stock Updated";
    }
}
