package com.example.userservice.service;

import com.example.userservice.User.ShopDTO;
import com.example.userservice.Entity.Shop;

public interface ShopService {
    Shop registerShop(ShopDTO shopDTO);
    Shop updateShop(ShopDTO shopDTO);
    Shop getShopById(Long id);
    Shop getShopBySellerId(Long sellerId);
}
