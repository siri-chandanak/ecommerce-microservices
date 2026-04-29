package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class ProductClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String PRODUCT_URL = "http://localhost:8082/api/products/";

    public Product getProduct(UUID productID) {
        try {
            return restTemplate.getForObject(
                    PRODUCT_URL + productID, Product.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Product not found");
        } catch (ResourceAccessException e) {
            throw new RuntimeException("Product service is down");
        }
    }
}
