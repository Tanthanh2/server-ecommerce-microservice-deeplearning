package com.example.purchaseservice.Service.impl;


import com.example.purchaseservice.Entity.Order;
import com.example.purchaseservice.Entity.Shipping;
import com.example.purchaseservice.Repository.OrderRepository;
import com.example.purchaseservice.Repository.ShippingRepository;
import com.example.purchaseservice.Request.ShippingRequest;
import com.example.purchaseservice.Service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Shipping addShipping(ShippingRequest shippingRequest) {
        Order order = orderRepository.findById(shippingRequest.getIdOrder())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Shipping shipping = Shipping.builder()
                .idDriver(shippingRequest.getIdDriver())
                .shippingDate(shippingRequest.getShippingDate())
                .deliveryDate(shippingRequest.getDeliveryDate())
                .status(shippingRequest.getStatus())
                .notes(shippingRequest.getNotes())
                .cost(shippingRequest.getCost())
                .order(order)
                .build();

        return shippingRepository.save(shipping);
    }

    @Override
    public Optional<Shipping> getShippingById(Long id) {
        return shippingRepository.findById(id);
    }

    @Override
    public Optional<Shipping> getShippingByOrderId(Long orderId) {
        return shippingRepository.findByOrderId(orderId);
    }

    @Override
    public Shipping updateShippingStatus(Long id, String status) {
        Shipping shipping = shippingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shipping not found"));

        if (status.equals("ĐANG GIAO")) {
            shipping.setDeliveryDate(new Date());
        } else if (status.equals("ĐÃ GIAO")) {
            shipping.setShippingDate(new Date());
        }

        shipping.setStatus(status);
        return shippingRepository.save(shipping);
    }
}