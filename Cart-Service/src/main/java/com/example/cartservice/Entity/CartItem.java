package com.example.cartservice.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _id;
    private Long idProduct;
    private Long idSizeQuantity;

    private int quantity;


    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
}

