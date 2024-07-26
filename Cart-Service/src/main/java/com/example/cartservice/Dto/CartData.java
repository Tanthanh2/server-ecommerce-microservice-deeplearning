package com.example.cartservice.Dto;

import com.example.cartservice.Dto.Order_Cart.ProductReponseCart_Order;
import com.example.cartservice.Dto.Order_Cart.SizeQuantityReponseCart_Order;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartData {
    private String _id;
    private int buy_count;
    private double price;
    private double price_before_discount;
    private int quantity;
    private int status;
    private String user;
    private ProductReponseCart_Order product;
    private SizeQuantityReponseCart_Order sizeQuantity;

}
