package com.example.purchaseservice.Request;

import com.example.purchaseservice.Entity.OrderItem;
import com.example.purchaseservice.Entity.Payment;
import com.example.purchaseservice.Entity.Shipping;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private Long id;
    private Long customerId;
    private Long shopId;
    private double totalMoney;
    private String status; // Trạng thái hiện tại của đơn hàng (ví dụ: pending, paid, shipped, delivered, cancelled)

    private List<OrderItemRequest> orderItems;
}
