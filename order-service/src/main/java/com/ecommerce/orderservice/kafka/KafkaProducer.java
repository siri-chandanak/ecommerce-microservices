package com.ecommerce.orderservice.kafka;

import com.ecommerce.orderservice.dto.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOPIC = "order-events";

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(OrderEvent event) {
        try {
            byte[] data = objectMapper.writeValueAsBytes(event);
            kafkaTemplate.send(TOPIC, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send event", e);
        }
    }
}