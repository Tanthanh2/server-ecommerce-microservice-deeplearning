package com.example.productservice.Service;

import com.example.productservice.Entity.Product;
import com.example.productservice.Reponse.Order.OrderItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ConsumerService {
    @Autowired
    private ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = "updatequantityproduct", containerFactory = "kafkaListenerJsonFactory", groupId = "tpd-loggers1")
    public void consumeSuperHero(List<OrderItemRequest> superHero) {
        logger.info("**** -> Consumed Super Hero :: {}", superHero);
        productService.HandleQuantityProduct(superHero);
    }
}
