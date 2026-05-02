package com.ecommerce.orderservice.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String PAYMENT_URL = "http://localhost:8084/api/payments";

    public void processPayment(double amount) {
        try {
            restTemplate.postForObject(
                    PAYMENT_URL + "?amount=" + amount,
                    null,
                    String.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Payment failed");
        }
    }
    public void refund(double amount) {
        restTemplate.postForObject(
                "http://localhost:8084/api/payments/refund?amount=" + amount,
                null,
                String.class
        );
    }
}
