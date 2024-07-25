package com.example.purchaseservice.Service;

import com.example.purchaseservice.Entity.Order;
import com.example.purchaseservice.Request.OrderRequest;

public interface OrderService {
    public Order createOrder(OrderRequest orderRequest);
}
