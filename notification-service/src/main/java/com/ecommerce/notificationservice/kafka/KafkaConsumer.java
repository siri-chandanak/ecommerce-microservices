package com.ecommerce.notificationservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consume(OrderEvent event) {

        System.out.println("Received event: " + event);

        // simulate notification
        System.out.println("Sending notification for order: " + event.getOrderId());
    }
}
