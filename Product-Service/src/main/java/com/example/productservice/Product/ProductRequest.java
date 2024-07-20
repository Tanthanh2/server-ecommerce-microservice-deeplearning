package com.example.productservice.Product;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private Long id;
    private List<String> images;
    private double price;
    private double priceBeforeDiscount;
    private int quantity = 0;
    private String name;
    private String description;
    private String shortDescription;
    private Long categoryId;
    private String image = "";
    private String createdAt;
    private String updatedAt;
    private Long idShop;
    private boolean isPublic = true;
    private double length;
    private double width;
    private double hight;
    private double weight;
    private List<SizeQuantityRequest> sizeQuantities;
}
