package com.example.productservice.Repository;

import com.example.productservice.Entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview,Long> {
    List<ProductReview> findByProductId(Long productId);
}
