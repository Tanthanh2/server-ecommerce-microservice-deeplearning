package com.example.purchaseservice.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/purchases/payments")
public class PaymentController {

    @GetMapping("/test")
    public String testApi() {
        return "payment Service API is working!";
    }
}
