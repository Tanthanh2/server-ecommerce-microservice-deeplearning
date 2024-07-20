package com.example.productservice.Repository;

import com.example.productservice.Product.ProductRequest;
import com.example.productservice.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductRequest productRequest);

    @Mapping(source = "category.id", target = "categoryId")
    ProductRequest toRequest(Product product);
}