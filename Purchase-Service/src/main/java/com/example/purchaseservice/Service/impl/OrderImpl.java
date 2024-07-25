package com.example.purchaseservice.Service.impl;

import com.example.purchaseservice.Entity.Order;
import com.example.purchaseservice.Entity.OrderItem;
import com.example.purchaseservice.Repository.OrderRepository;
import com.example.purchaseservice.Request.OrderRequest;
import com.example.purchaseservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Convert OrderRequest to Order entity
    private Order mapToOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerId(orderRequest.getCustomerId())
                .promotionId(orderRequest.getPromotionId())
                .shopId(orderRequest.getShopId())
                .totalMoney(orderRequest.getTotalMoney())
                .status(orderRequest.getStatus())
                .orderDate(new Date()) // Set current date
                .build();

        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> OrderItem.builder()
                        .productId(orderItemRequest.getProductId())
                        .idSizeQuantity(orderItemRequest.getIdSizeQuantity())
                        .quantity(orderItemRequest.getQuantity())
                        .order(order) // Link back to the Order entity
                        .build())
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        // Optionally set Payment and Shipping if they exist in the request

        return order;
    }

    public Order createOrder(OrderRequest orderRequest) {
        Order order = mapToOrder(orderRequest);
        return orderRepository.save(order);
    }
}
