package com.example.purchaseservice.Service.impl;

import com.example.purchaseservice.Entity.Order;
import com.example.purchaseservice.Entity.OrderItem;

import com.example.purchaseservice.Repository.OrderRepository;
import com.example.purchaseservice.Request.OrderRequest;
import com.example.purchaseservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;



    // Convert OrderRequest to Order entity
    private Order mapToOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerId(orderRequest.getCustomerId())
                .shopId(orderRequest.getShopId())
                .totalMoney(orderRequest.getTotalMoney())
                .status(orderRequest.getStatus())
                .orderDate(new Date()) // Set current date
                .build();

        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> OrderItem.builder()
                        .productId(orderItemRequest.getProductId())
                        .idSizeQuantity(orderItemRequest.getIdSizeQuantity())
                        .promotionId(orderItemRequest.getPromotionId())
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

    @Override
    public Order updateOrderStatus(Long id, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found with id " + id);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> getOrdersByShopId(Long shopId) {
        return orderRepository.findByShopId(shopId);
    }
}
