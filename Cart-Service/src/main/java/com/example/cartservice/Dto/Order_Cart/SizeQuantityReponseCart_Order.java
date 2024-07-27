package com.example.cartservice.Dto.Order_Cart;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeQuantityReponseCart_Order {
    private Long id;
    private String size;
    private String color;
    private int quantity;
}