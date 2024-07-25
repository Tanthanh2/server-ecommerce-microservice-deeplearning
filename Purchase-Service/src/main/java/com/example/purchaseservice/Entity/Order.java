package com.example.purchaseservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;
    private Long promotionId;
    private Long shopId;

    @Column(nullable = false, updatable = false)
    private Date orderDate;
    private double totalMoney;
    private String status; // Trạng thái hiện tại của đơn hàng (ví dụ: pending, paid, shipped, delivered, cancelled)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Payment payment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Shipping shipping;

    @PrePersist
    protected void onCreate() {
        this.orderDate = new Date(); // Set the order date to the current date and time
    }
}
