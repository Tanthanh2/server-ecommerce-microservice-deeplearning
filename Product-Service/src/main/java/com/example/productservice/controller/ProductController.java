package com.example.productservice.controller;

import com.example.productservice.Entity.Product;
import com.example.productservice.Reponse.Pagination;
import com.example.productservice.Reponse.ProductData;
import com.example.productservice.Reponse.ProductReponSingle;
import com.example.productservice.Reponse.ProductReponse;
import com.example.productservice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping("/test")
    public String testApi() {
        return "products Service API is working!";
    }

    @Autowired
    private ProductService productService;
    @PatchMapping("/{id}/increment-view")
    public ResponseEntity<Void> incrementProductView(@PathVariable Long id) {
        productService.incrementProductView(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReponSingle> getProductById(@PathVariable Long id) {
        Product product = productService.getById(id);
        ProductReponSingle productReponSingle = new ProductReponSingle();
        productReponSingle.setMessage("Lấy sản phẩm thành công");
        productReponSingle.setData(product);
        return new ResponseEntity<>(productReponSingle, HttpStatus.OK);
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<Product>> getProductsByShopId(@PathVariable Long shopId) {
        List<Product> products = productService.getListByShopId(shopId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?>  searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long idcategory,
            @RequestParam(required = false) Double price_min,
            @RequestParam(required = false) Double price_max,
            @RequestParam(required = false, defaultValue = "price") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false) Integer rating_filter,
            @RequestParam(defaultValue = "0") int page, // Giá trị mặc định là trang 0
            @RequestParam(defaultValue = "10") int size) { // Giá trị mặc định là kích thước trang 10

        Sort sort = Sort.by(Sort.Order.asc(sortBy));
        if ("desc".equalsIgnoreCase(order)) {
            sort = Sort.by(Sort.Order.desc(sortBy));
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products=  productService.findAllWithFiltersAndSorting(name, idcategory, price_min, price_max, sortBy, order, rating_filter, pageable);
        List<Product> list = products.stream().toList();
        Pagination pagination = new Pagination(pageable.getPageNumber(),0, pageable.getPageSize());
        ProductData productData = new ProductData(list,pagination);
        ProductReponse p = new ProductReponse("Thành Công", productData);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
