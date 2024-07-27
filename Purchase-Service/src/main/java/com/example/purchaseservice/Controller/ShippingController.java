package com.example.purchaseservice.Controller;

import com.example.purchaseservice.Entity.Shipping;
import com.example.purchaseservice.Request.ShippingRequest;
import com.example.purchaseservice.Service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/purchases/shippings")
public class ShippingController {
    @GetMapping("/test")
    public String testApi() {
        return "shipping Service API is working!";
    }

    @Autowired
    private ShippingService shippingService;

    @PostMapping
    public ResponseEntity<Shipping> addShipping(@RequestBody ShippingRequest shippingRequest) {
        Shipping shipping = shippingService.addShipping(shippingRequest);
        return ResponseEntity.ok(shipping);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getShippingById(@PathVariable Long id) {
        Optional<Shipping> shipping = shippingService.getShippingById(id);
        return shipping.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Shipping> getShippingByOrderId(@PathVariable Long orderId) {
        Optional<Shipping> shipping = shippingService.getShippingByOrderId(orderId);
        return shipping.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Shipping> updateShippingStatus(@PathVariable Long id, @RequestParam String status) {
        Shipping shipping = shippingService.updateShippingStatus(id, status);
        return ResponseEntity.ok(shipping);
    }
}
