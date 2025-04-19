package com.example.wslkafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = "demo-topic", groupId = "test-group")
    public void listen(String message) {
        System.out.println("Mensaje recibido: " + message);
    }
}