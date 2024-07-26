package com.example.productservice.Service.impl;

import com.example.productservice.Entity.SizeQuantity;
import com.example.productservice.Product.Order_Cart.ProductReponseCart_Order;
import com.example.productservice.Product.Order_Cart.SizeQuantityReponseCart_Order;
import com.example.productservice.Product.ProductRequest;
import com.example.productservice.Product.SizeQuantityRequest;
import com.example.productservice.Entity.Category;
import com.example.productservice.Entity.Product;
import com.example.productservice.Reponse.ProductReponse;
import com.example.productservice.Reponse.ProductWithSizeQuantityReponse;
import com.example.productservice.Repository.CategoryRepository;
import com.example.productservice.Repository.ProductMapper;
import com.example.productservice.Repository.ProductRepository;
import com.example.productservice.Repository.SizeQuantityRepository;
import com.example.productservice.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Autowired
    private SizeQuantityRepository sizeQuantityRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product addProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = ProductMapper.toEntity(productRequest, category);
        if(productRequest.getSizeQuantities() != null && productRequest.getSizeQuantities().size() != 0){
            for (SizeQuantity sizeQuantity : product.getSizeQuantities()) {
                sizeQuantity.setProduct(product);
            }
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProductBasic(Long id, ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            Product product1 = product.get();
            product1.setCategory(category);
            product1.setImages(productRequest.getImages());
            product1.setImage(productRequest.getImage());
            product1.setName(productRequest.getName());
            product1.setDescription(productRequest.getDescription());
            return productRepository.save(product1);
        }else {
            throw new RuntimeException("Product not found");
        }

    }

    @Override
    public Product updateProductDetail(Long id, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            Product product1 = product.get();
            product1.setShortDescription(productRequest.getShortDescription());
            return productRepository.save(product1);
        }else {
            throw new RuntimeException("Product not found");
        }

    }

    @Override
    public Product updateProductSell(Long id, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            Product product1 = product.get();
            product1.setPrice(productRequest.getPrice());
            if(productRequest.getSizeQuantities() == null || productRequest.getSizeQuantities().size() == 0){
                product1.setQuantity(productRequest.getQuantity());
                return productRepository.save(product1);

            }else {
// Remove sizeQuantities not in the request
                product1.setQuantity(productRequest.getQuantity());

                List<SizeQuantity> currentSizeQuantities = product1.getSizeQuantities();
                currentSizeQuantities.removeIf(sizeQuantity ->
                        productRequest.getSizeQuantities().stream().noneMatch(req -> req.getId() != null && req.getId().equals(sizeQuantity.getId())));

                for (SizeQuantityRequest request : productRequest.getSizeQuantities()) {
                    SizeQuantity sizeQuantity;
                    if (request.getId() != null) {
                        // Update existing SizeQuantity
                        sizeQuantity = sizeQuantityRepository.findById(request.getId())
                                .orElseThrow(() -> new RuntimeException("SizeQuantity not found"));
                        sizeQuantity.setSize(request.getSize());
                        sizeQuantity.setColor(request.getColor());
                        sizeQuantity.setQuantity(request.getQuantity());
                    } else {
                        // Add new SizeQuantity
                        sizeQuantity = new SizeQuantity();
                        sizeQuantity.setSize(request.getSize());
                        sizeQuantity.setColor(request.getColor());
                        sizeQuantity.setQuantity(request.getQuantity());
                        sizeQuantity.setProduct(product1);
                        product1.getSizeQuantities().add(sizeQuantity);
                    }
                    sizeQuantityRepository.save(sizeQuantity);
                }

                return productRepository.save(product1);
            }


        }else {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public void deleteSizeQuantityById(Long id) {
        sizeQuantityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SizeQuantity not found with id: " + id));
        sizeQuantityRepository.deleteById(id);
    }

    @Override
    public Product updateProductShip(Long id, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            Product product1 = product.get();
            product1.setWeight(productRequest.getWeight());
            product1.setHight(productRequest.getHight());
            product1.setWidth(productRequest.getWidth());
            product1.setLength(productRequest.getLength());
            return productRepository.save(product1);
        }else {
            throw new RuntimeException("Product not found");
        }
    }


    @Override
    public List<Product> getListByShopId(Long shopId) {
        return productRepository.findByIdShop(shopId);
    }

    @Override
    public SizeQuantity findByIdSizeQuantity(Long id) {
        Optional<SizeQuantity> sizeQuantity = sizeQuantityRepository.findById(id);
        return sizeQuantity.orElse(null);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Page<Product> findAllWithFiltersAndSorting(String name, Long idcategory, Double price_min, Double price_max, String sortBy, String order, Integer rating_filter, Pageable pageable) {
        return productRepository.findAllWithFiltersAndSorting(name, idcategory, price_min, price_max, sortBy, order, rating_filter, pageable);
    }

    @Override
    public ProductWithSizeQuantityReponse findProductWithSize(Long idProduct, Long idSizeQuantity) {
        ProductWithSizeQuantityReponse product = new ProductWithSizeQuantityReponse();

        Product product1 = this.getById(idProduct);
        SizeQuantity sizeQuantity = this.findByIdSizeQuantity(idSizeQuantity);
        if (product1 == null) {
            return null; // Handle not found case
        }

        ProductReponseCart_Order productResponse = modelMapper.map(product1, ProductReponseCart_Order.class);
        SizeQuantityReponseCart_Order sizeQuantityResponse = sizeQuantity != null ? modelMapper.map(sizeQuantity, SizeQuantityReponseCart_Order.class) : null;


        return ProductWithSizeQuantityReponse.builder()
                .product(productResponse)
                .sizeQuantity(sizeQuantityResponse)
                .build();
    }

    @Override
    public void updateIsPublic(Long id, boolean isPublic) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product product1 = product.get();
            product1.setPublic(isPublic);
            productRepository.save(product1);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public void incrementProductView(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setView(product.getView() + 1);
            productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

}
