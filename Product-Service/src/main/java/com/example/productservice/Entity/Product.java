package com.example.productservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ElementCollection
    private List<String> images;
    private double price;
    private double rating = 0;
    private double priceBeforeDiscount;
    private int quantity;
    private int sold = 0;
    private int view = 0;
    private int order = 0;
    private String name;
    private String description;
    private String shortDescription;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;
    private String image;
    private String createdAt;
    private String updatedAt;
    private Long idShop;
    private boolean isPublic = true;

    private double length;
    private double width;
    private double hight;
    private double weight;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SizeQuantity> sizeQuantities; // Cho phép null
}
