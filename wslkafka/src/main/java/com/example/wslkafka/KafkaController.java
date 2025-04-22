package com.example.wslkafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/sendFromDb/{id}")
    public ResponseEntity<String> sendFromDb(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(message -> {
                    kafkaTemplate.send("demo-topic", message.getContenido());
                    return ResponseEntity.ok("Mensaje enviado: " + message.getContenido());
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
