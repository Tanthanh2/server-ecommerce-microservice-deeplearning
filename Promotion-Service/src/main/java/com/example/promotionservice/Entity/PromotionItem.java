package com.example.promotionservice.Entity;

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
    private Long idProduct;

    @ManyToMany(mappedBy = "promotionItemList")
    @JsonIgnore
    private List<Promotion> promotions;
}
