package com.example.shippingservice.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _id;

    private Long idDriver;
    private Long orderId;

    private Date shippingDate;
    private Date deliveryDate;
    private  String status;
    private String method;
    private String notes;
    private double cost;
}
