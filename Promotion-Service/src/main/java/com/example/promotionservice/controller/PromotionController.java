package com.example.promotionservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {
    @GetMapping("/test")
    public String testApi() {
        return "products Service API is working!";
    }
}
