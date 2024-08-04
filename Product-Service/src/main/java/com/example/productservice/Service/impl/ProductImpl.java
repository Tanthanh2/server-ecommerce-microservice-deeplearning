package com.example.productservice.Service.impl;

import com.example.productservice.Entity.Promotion;
import com.example.productservice.Entity.SizeQuantity;
import com.example.productservice.Product.Order_Cart.ProductReponseCart_Order;
import com.example.productservice.Product.Order_Cart.SizeQuantityReponseCart_Order;
import com.example.productservice.Product.ProductRequest;
import com.example.productservice.Product.SizeQuantityRequest;
import com.example.productservice.Entity.Category;
import com.example.productservice.Entity.Product;
import com.example.productservice.Reponse.Order.OrderData;
import com.example.productservice.Reponse.Order.OrderDataRequest;
import com.example.productservice.Reponse.Order.OrderItemRequest;
import com.example.productservice.Reponse.Order.PromotionData;
import com.example.productservice.Reponse.Product_Promotion_SizeQuantityy_GET;
import com.example.productservice.Reponse.PromotionRequest;
import com.example.productservice.Reponse.ReponseOrder.ReponseOrder;
import com.example.productservice.Reponse.ReponseOrder.ReponseOrderData;
import com.example.productservice.Repository.*;
import com.example.productservice.Service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private PromotionRepository promotionRepository;
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
                product1.setSizeQuantities(new ArrayList<>());
                return productRepository.save(product1);

            }else {
// Remove sizeQuantities not in the request
                product1.setQuantity(productRequest.getQuantity());

                // Find size quantities to be removed
                List<SizeQuantity> toRemove = new ArrayList<>();

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
    public Product updateProductShip(Long id, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            Product product1 = product.get();
            product1.setWeight(productRequest.getWeight());
            product1.setHeight(productRequest.getHeight());
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
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Insert new product"));
    }

    @Override
    public Page<Product> findAllWithFiltersAndSorting(String name, Long idcategory, Double price_min, Double price_max, String sortBy, String order, Integer rating_filter, Pageable pageable) {
        return productRepository.findAllWithFiltersAndSorting(name, idcategory, price_min, price_max, sortBy, order, rating_filter, pageable);
    }

    @Override
    public Product findProductWithSize(Long idProduct, Long idSizeQuantity) {
        Product product1 = this.getById(idProduct);
        return product1;
    }

    @Override
    public Product_Promotion_SizeQuantityy_GET Product_Promotion_SizeQuantityy_GET_(Long idproduct, Long idSizeQuantity, Long idPromotion, int quantity) {


        Product product1 = this.getById(idproduct);
        if (product1 == null) {
            return null; // Handle not found case
        }

        if(idSizeQuantity == null){
            idSizeQuantity = 0l;
        }
        SizeQuantity sizeQuantity = this.findByIdSizeQuantity(idSizeQuantity);


        Promotion promotion1 =null;
        if(idPromotion != null){
            Optional<Promotion> promotion = promotionRepository.findById(idPromotion);
            if(promotion.isPresent()) {
                promotion1 = promotion.get();
            }
        }



        ProductReponseCart_Order productResponse = modelMapper.map(product1, ProductReponseCart_Order.class);
        SizeQuantityReponseCart_Order sizeQuantityResponse = sizeQuantity != null ? modelMapper.map(sizeQuantity, SizeQuantityReponseCart_Order.class) : null;
        PromotionRequest promotionRequest = promotion1 != null ? modelMapper.map(promotion1, PromotionRequest.class) : null;

        return Product_Promotion_SizeQuantityy_GET.builder()
                .product(productResponse)
                .promotionRequest(promotionRequest)
                .sizeQuantity(sizeQuantityResponse)
                .quantity(quantity)
                .build();
    }

    @Override
    public List<Product_Promotion_SizeQuantityy_GET> getOderDetails(ReponseOrderData reponseOrderData) {
        List<Product_Promotion_SizeQuantityy_GET> list = new ArrayList<>();
        List<ReponseOrder> reponseOrderList = reponseOrderData.getOrderItems();

        for(ReponseOrder order : reponseOrderList){
            Product_Promotion_SizeQuantityy_GET productPromotionSizeQuantityyGet= this.Product_Promotion_SizeQuantityy_GET_(
                    order.getProductId(),order.getIdSizeQuantity(),order.getPromotionId(),order.getQuantity()
            );
            list.add(productPromotionSizeQuantityyGet);
        }
        return list;
    }

    @Override
    public void plusView(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {
            p.setView(p.getView() + 1);
            productRepository.save(p);
        });
    }

    @Override
    public List<OrderData> listOrderData(List<OrderDataRequest> orderDataRequests) {
        List<OrderData> list = new ArrayList<>();

        for (OrderDataRequest o : orderDataRequests) {
            // Fetch product by productId
            Product product = this.getById(o.getProductId());

            // Initialize promotion variable
            Promotion promotion = null;

            // Check if promotionId is not null before querying the repository
            if (o.getPromotionId() != null) {
                Optional<Promotion> promotionOptional = promotionRepository.findById(o.getPromotionId());
                if (promotionOptional.isPresent()) {
                    promotion = promotionOptional.get();
                }
            }

            // Map promotion to PromotionData if it exists
            PromotionData promotionRequest = promotion != null ? modelMapper.map(promotion, PromotionData.class) : null;

            // Create OrderData object and use default values for null fields
            OrderData orderData = new OrderData(
                    o.getId(),
                    product,
                    o.getIdSizeQuantity() != null ? o.getIdSizeQuantity() : 0, // Use a default value if null
                    promotionRequest,
                    o.getQuantity() != null ? o.getQuantity() : 0, // Use a default value if null
                    o.getNote() != null ? o.getNote() : "" // Use an empty string if null
            );

            list.add(orderData);
        }
        return list;
    }

    @Override
    public Void HandleQuantityProduct(List<OrderItemRequest> orderItemRequests) {
        if (orderItemRequests == null || orderItemRequests.isEmpty()) {
            return null; // or throw an IllegalArgumentException
        }

        for (OrderItemRequest o : orderItemRequests) {
            Product product = this.getById(o.getProductId());
            if (o.getIdSizeQuantity() != null && o.getIdSizeQuantity() != 0) {
                // Handle SizeQuantity
                SizeQuantity sizeQuantity = sizeQuantityRepository.findById(o.getIdSizeQuantity())
                        .orElseThrow(() -> new EntityNotFoundException("SizeQuantity not found"));

                if (o.getId() == 0) {
                    sizeQuantity.setQuantity(sizeQuantity.getQuantity() - o.getQuantity());
                } else {
                    sizeQuantity.setQuantity(sizeQuantity.getQuantity() + o.getQuantity());
                }
                sizeQuantityRepository.save(sizeQuantity);
            }

            if (o.getId() == 0) {
                // Order is successful
                product.setQuantity(product.getQuantity() - o.getQuantity());
            } else {
                // Order is not successful
                product.setQuantity(product.getQuantity() + o.getQuantity());
            }
            productRepository.save(product);
        }
        return null;
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

    @Transactional
    @Override
    public void deleteSizeQuantityById(Long id) {
        // Fetch the SizeQuantity entity by ID
        SizeQuantity sizeQuantity = sizeQuantityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SizeQuantity not found with ID: " + id));

        // Get the associated Product
        Product product = sizeQuantity.getProduct();

        // Update the Product's quantity
        product.setQuantity(product.getQuantity() - sizeQuantity.getQuantity());

        // Remove SizeQuantity from Product's sizeQuantities list
        List<SizeQuantity> sizeQuantities = product.getSizeQuantities();
        sizeQuantities.remove(sizeQuantity); // Use remove() directly on the list

        // Save the updated Product
        productRepository.save(product);

        // Delete the SizeQuantity entity
        sizeQuantityRepository.delete(sizeQuantity);
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
