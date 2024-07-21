package com.example.purchaseservice.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long productId;
    private Long idSizeQuantity; // cho phép null nếu là các sản phẩm k có size
    private int quantity;


    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}
