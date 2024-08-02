package com.example.productservice.controller.Seller;

import com.example.productservice.Entity.Product;
import com.example.productservice.Product.ProductRequest;
import com.example.productservice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products/seller")
public class ProductControllerSeller {
    @Autowired
    private ProductService productService;

    // add
    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest product) {
        Product newProduct = productService.addProduct(product);
        if(newProduct != null){
            return new ResponseEntity<>("Insert Success", HttpStatus.CREATED);

        }else {
            return new ResponseEntity<>("Insert NOT Success", HttpStatus.BAD_REQUEST);
        }
    }

    // update

    @PutMapping("/{id}/basic")
    public ResponseEntity<Product> updateProductBasic(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product updatedProduct = productService.updateProductBasic(id, productRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
    @PutMapping("/{id}/detail")
    public ResponseEntity<Product> updateProductDetail(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product updatedProduct = productService.updateProductDetail(id, productRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
    @PutMapping("/{id}/sell")
    public ResponseEntity<Product> updateProductSell(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product updatedProduct = productService.updateProductSell(id, productRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/sizequantity")
    public ResponseEntity<Void> deleteSizeQuantity(@PathVariable Long id) {
        productService.deleteSizeQuantityById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/ship")
    public ResponseEntity<Product> updateProductShip(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product updatedProduct = productService.updateProductShip(id, productRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // hidden product
    @PatchMapping("/{id}/public")
    public ResponseEntity<Void> updateIsPublic(@PathVariable Long id, @RequestParam boolean isPublic) {
        productService.updateIsPublic(id, isPublic);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
