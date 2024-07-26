package com.example.cartservice.Dto.Order_Cart;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductWithSizeQuantityReponse {
    private ProductReponseCart_Order product;
    private SizeQuantityReponseCart_Order sizeQuantity;
}
