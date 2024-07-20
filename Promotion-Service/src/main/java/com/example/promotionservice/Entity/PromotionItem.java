package com.example.promotionservice.Entity;

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
    private Long _id;

    private Long idProduct;

    @ManyToMany(mappedBy = "promotionItemList")
    private List<Promotion> promotions;
}
