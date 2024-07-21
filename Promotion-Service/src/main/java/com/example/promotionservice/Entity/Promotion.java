package com.example.promotionservice.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.LifecycleState;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Date startDate;
    private Date endDate;
    private String code;
    private String status;
    private String description;
    private double discountAmount;

    private Long idShop;

    @ManyToMany
    @JoinTable(
            name = "promotion_promotion_item",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_item_id")
    )
    private List<PromotionItem> promotionItemList;
}
