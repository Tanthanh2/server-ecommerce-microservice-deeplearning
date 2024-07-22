package com.example.productservice.Service.impl;

import com.example.productservice.Product.ProductRequest;
import com.example.productservice.Product.SizeQuantityRequest;
import com.example.productservice.Entity.Category;
import com.example.productservice.Entity.Product;
import com.example.productservice.Repository.CategoryRepository;
import com.example.productservice.Repository.ProductMapper;
import com.example.productservice.Repository.ProductRepository;
import com.example.productservice.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductImpl implements ProductService {
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  CategoryRepository categoryRepository;

    @Override
    public Product addProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = ProductMapper.toEntity(productRequest, category);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getListByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getListByShopId(Long shopId) {
        return productRepository.findByIdShop(shopId);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void updateIsPublic(Long id, boolean isPublic) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            Product product1 = product.get();
            product1.setPublic(isPublic);
        }
    }

    @Override
    public Product findAll(Pageable pageable, String name, String category, Double price_min, Double price_max, String sort_by, String order, Integer rating_filter) {
        return null;
    }
}
