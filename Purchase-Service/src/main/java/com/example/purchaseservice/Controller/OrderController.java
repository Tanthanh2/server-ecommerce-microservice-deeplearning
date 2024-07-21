package com.example.purchaseservice.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/purchases/orders")
public class OrderController {
    @GetMapping("/test")
    public String testApi() {
        return "Order Service API is working!";
    }
}
