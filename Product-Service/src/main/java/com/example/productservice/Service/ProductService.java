package com.example.productservice.Service;

import com.example.productservice.Product.ProductRequest;
import com.example.productservice.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductRequest productRequest);
    Product updateProductBasic(Long id, ProductRequest productRequest);
    Product updateProductDetail(Long id, ProductRequest productRequest);
    Product updateProductSell(Long id, ProductRequest productRequest);
    public void deleteSizeQuantityById(Long id) ;
    Product updateProductShip(Long id, ProductRequest productRequest);
    void updateIsPublic(Long id, boolean isPublic);

    public void incrementProductView(Long id);
    List<Product> getListByShopId(Long shopId);

    Product getById(Long id);

    Page<Product> findAllWithFiltersAndSorting(String name,
                                               Long idcategory,
                                               Double price_min,
                                               Double price_max,
                                               String sortBy,
                                               String order,
                                               Integer rating_filter,
                                               Pageable pageable);
}
