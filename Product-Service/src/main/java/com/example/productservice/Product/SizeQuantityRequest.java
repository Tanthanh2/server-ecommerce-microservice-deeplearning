package com.example.productservice.Product;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeQuantityRequest {
    private String size;
    private int quantity;
}