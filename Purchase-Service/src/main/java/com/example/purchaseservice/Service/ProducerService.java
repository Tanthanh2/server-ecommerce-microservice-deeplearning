package com.example.purchaseservice.Service;


import com.example.purchaseservice.Request.OrderItemRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ProducerService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TOPIC = "updatequantityproduct";

    @Autowired
    private KafkaTemplate<String, List<OrderItemRequest>> kafkaTemplateSuperHero;

    public void sendSuperHeroMessage(List<OrderItemRequest> superHero) {
        logger.info("#### -> Publishing SuperHero :: {}", superHero);
        kafkaTemplateSuperHero.send(TOPIC, superHero);
    }


}
