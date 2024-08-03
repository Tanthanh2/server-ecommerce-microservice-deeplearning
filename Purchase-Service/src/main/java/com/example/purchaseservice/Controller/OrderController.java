package com.example.purchaseservice.Controller;

import com.example.purchaseservice.Entity.Order;
import com.example.purchaseservice.Request.OrderRequest;
import com.example.purchaseservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/purchases/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.createOrder(orderRequest);
        if(createdOrder != null){
            return new ResponseEntity<>("Success", HttpStatus.CREATED);

        }else {
            return new ResponseEntity<>("Not Success", HttpStatus.CREATED);

        }
    }

    // update status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // get order BY customerId
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId, @RequestParam String status) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId, status);
        return ResponseEntity.ok(orders);
    }

    // get order By shopId
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<Order>> getOrdersByShopId(@PathVariable Long shopId, @RequestParam String status) {
        List<Order> orders = orderService.getOrdersByShopId(shopId, status);
        return ResponseEntity.ok(orders);
    }


}
