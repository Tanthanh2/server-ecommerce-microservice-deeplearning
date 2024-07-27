package com.example.purchaseservice.Request;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingRequest {
    private Long id;
    private Long idDriver;
    private Date shippingDate;
    private Date deliveryDate;
    private  String status; // CHƯA LẤY HÀNG, ĐANG GIAO, ĐÃ GIAO, TRẢ HÀNG
    private String notes;
    private double cost;
    private Long idOrder;
}
