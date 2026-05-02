package com.ecommerce.paymentservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @PostMapping
    public String processPayment(
            @RequestParam double amount,
            @RequestParam(defaultValue = "false") boolean fail
    ) {
        if (fail) {
            throw new RuntimeException("Payment failed");
        }

        System.out.println("PAYMENT SUCCESS for amount: " + amount);
        return "PAYMENT_SUCCESS";
    }

    @PostMapping("/refund")
    public String refund(@RequestParam double amount) {
        System.out.println("REFUND TRIGGERED for amount: " + amount);
        return "REFUND_SUCCESS";
    }
}