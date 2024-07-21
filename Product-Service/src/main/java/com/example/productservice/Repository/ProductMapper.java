package com.example.productservice.Repository;

import com.example.productservice.Product.ProductRequest;
import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.Category;
import com.example.productservice.Entity.SizeQuantity;
import com.example.productservice.Product.SizeQuantityRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toEntity(ProductRequest productRequest, Category category) {
        return Product.builder()
                .id(productRequest.getId())
                .images(productRequest.getImages())
                .price(productRequest.getPrice())
                .priceBeforeDiscount(productRequest.getPriceBeforeDiscount())
                .quantity(productRequest.getQuantity())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .shortDescription(productRequest.getShortDescription())
                .category(category)
                .image(productRequest.getImage())
                .createdAt(productRequest.getCreatedAt() != null ? LocalDateTime.parse(productRequest.getCreatedAt()) : null)
                .updatedAt(productRequest.getUpdatedAt() != null ? LocalDateTime.parse(productRequest.getUpdatedAt()) : null)
                .idShop(productRequest.getIdShop())
                .isPublic(productRequest.isPublic())
                .length(productRequest.getLength())
                .width(productRequest.getWidth())
                .hight(productRequest.getHight())
                .weight(productRequest.getWeight())
                .sizeQuantities(toSizeQuantityEntities(productRequest.getSizeQuantities()))
                .build();
    }

    private static List<SizeQuantity> toSizeQuantityEntities(List<SizeQuantityRequest> sizeQuantityRequests) {
        if (sizeQuantityRequests == null) {
            return null;
        }
        return sizeQuantityRequests.stream()
                .map(sizeQuantityRequest -> SizeQuantity.builder()
                        .id(sizeQuantityRequest.getId())
                        .size(sizeQuantityRequest.getSize())
                        .color(sizeQuantityRequest.getColor())
                        .quantity(sizeQuantityRequest.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }
}