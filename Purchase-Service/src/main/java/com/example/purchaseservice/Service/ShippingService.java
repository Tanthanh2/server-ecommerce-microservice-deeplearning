package com.example.purchaseservice.Service;



import com.example.purchaseservice.Entity.Shipping;
import com.example.purchaseservice.Request.ShippingRequest;

import java.util.Optional;

public interface ShippingService {
    Shipping addShipping(ShippingRequest shippingRequest);
    Optional<Shipping> getShippingById(Long id);
    Optional<Shipping> getShippingByOrderId(Long orderId);
    Shipping updateShippingStatus(Long id, String status);
}