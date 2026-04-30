package com.ecommerce.paymentservice.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @PostMapping
    public String processPayment(@RequestParam double amount)
    {
        if(Math.random()<0.5)
        {
            return "Payment Failed";
        }
        return "Payment Success";
    }
}
