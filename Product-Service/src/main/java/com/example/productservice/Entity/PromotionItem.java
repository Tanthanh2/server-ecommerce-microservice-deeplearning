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
public class PromotionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne

    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToMany(mappedBy = "promotionItemList")
    @JsonIgnore
    private List<Promotion> promotions;
}
