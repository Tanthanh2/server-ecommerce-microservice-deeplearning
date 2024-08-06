package com.example.productservice.Service;

import com.example.productservice.Reponse.Order.OrderItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ConsumerService {
    @Autowired
    private ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = "updatequantityproduct", containerFactory = "kafkaListenerStringFactory", groupId = "tpd-loggers1")
    public void consumeMessage(String message) {
        logger.info("**** -> Consumed message -> {}", message);
        productService.HandleQuantityProductAdd(message);
    }

    @KafkaListener(topics = "updatequantityproduct1", containerFactory = "kafkaListenerStringFactory", groupId = "tpd-loggers1")
    public void consumeMessage1(String message) {
        logger.info("**** -> Consumed message -> {}", message);
        productService.HandleQuantityProductSub(message);
    }

}
