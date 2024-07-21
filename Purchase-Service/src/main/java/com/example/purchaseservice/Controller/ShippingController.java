package com.example.purchaseservice.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/purchases/shippings")
public class ShippingController {
    @GetMapping("/test")
    public String testApi() {
        return "shipping Service API is working!";
    }
}
