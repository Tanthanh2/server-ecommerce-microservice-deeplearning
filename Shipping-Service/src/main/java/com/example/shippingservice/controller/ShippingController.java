package com.example.shippingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shippings")
public class ShippingController {
    @GetMapping("/test")
    public String testApi() {
        return "shipping Service API is working!";
    }
}
