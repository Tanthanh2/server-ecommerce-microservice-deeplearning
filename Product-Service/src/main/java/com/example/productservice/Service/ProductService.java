package com.example.productservice.Service;

import com.example.productservice.Product.ProductRequest;
import com.example.productservice.Entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductRequest productRequest);
    List<Product> getListByCategoryId(Long categoryId);
    List<Product> getListByShopId(Long shopId);
    Product getById(Long id);
    void updateIsPublic(Long id, boolean isPublic);
    public Product findAll(Pageable pageable, String name, String category, Double price_min, Double price_max, String  sort_by, String order, Integer rating_filter);
}
