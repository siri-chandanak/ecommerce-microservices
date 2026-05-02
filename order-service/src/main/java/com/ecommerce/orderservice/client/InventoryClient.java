package com.ecommerce.orderservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class InventoryClient {
    @Autowired
    private RestTemplate restTemplate;

    private final String URL = "http://localhost:8085/api/inventory/reduce";

    public void reduce(UUID productId, int quantity) {
        try {
            restTemplate.postForObject(
                    URL + "?productId=" + productId + "&quantity=" + quantity,
                    null,
                    String.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Inventory failed");
        }
    }
}
