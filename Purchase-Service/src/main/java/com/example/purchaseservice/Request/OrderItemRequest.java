package com.example.purchaseservice.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {
    private Long id;
    private Long productId;
    private Long idSizeQuantity; // cho phép null nếu là các sản phẩm k có size
    private int quantity;
}
