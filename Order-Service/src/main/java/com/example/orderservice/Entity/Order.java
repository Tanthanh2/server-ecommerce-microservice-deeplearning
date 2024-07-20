package com.example.orderservice.Entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Long _id;

    private Long customerId;
    private Long promotionId;
    private Long shopId;

    private Date orderDate;
    private String orderNotes;
    private double totalMoney;
    private String status; // Trạng thái hiện tại của đơn hàng (ví dụ: pending, paid, shipped, delivered, cancelled)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
