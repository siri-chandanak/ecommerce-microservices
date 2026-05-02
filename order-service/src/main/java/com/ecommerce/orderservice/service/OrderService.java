package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.InventoryClient;
import com.ecommerce.orderservice.client.PaymentClient;
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

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private InventoryClient inventoryClient;

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
        order.setCreatedAt(LocalDateTime.now());

        try {
            paymentClient.processPayment(total);

            try {
                inventoryClient.reduce(product.getId(), request.getQuantity());

                order.setStatus("COMPLETED");

            } catch (Exception inventoryError) {

                paymentClient.refund(total);

                order.setStatus("FAILED");
            }

        } catch (Exception paymentError) {
            order.setStatus("FAILED");
        }

        orderRepository.save(order);
        return order;
    }

}
