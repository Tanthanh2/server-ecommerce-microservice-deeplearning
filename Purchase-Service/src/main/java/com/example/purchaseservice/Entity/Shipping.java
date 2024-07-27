package com.example.purchaseservice.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long idDriver;
    private Date shippingDate;
    private Date deliveryDate;
    private  String status; // CHƯA LẤY HÀNG, ĐANG GIAO, ĐÃ GIAO, TRẢ HÀNG
    private String notes;
    private double cost;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;
}
