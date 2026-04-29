package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.Product;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    public Order createOrder(OrderRequest request)
    {
        Product product = productClient.getProduct(request.getProductId());
        if(product==null)
        {
           throw new RuntimeException("Product not found");
        }
        double total = product.getPrice() * request.getQuantity();

        Order order = new Order();
        order.setProductId(product.getId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(total);
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);

        return order;
    }
}
