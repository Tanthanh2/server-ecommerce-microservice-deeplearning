package com.example.userservice.Controller;


import com.example.userservice.User.ShopDTO;
import com.example.userservice.Entity.Shop;
import com.example.userservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/shop")
public class ShopController {
    @Autowired
    private  ShopService shopService;

    @PostMapping
    public ResponseEntity<Shop> registerShop(@RequestBody ShopDTO shopDTO) {
        Shop shop = shopService.registerShop(shopDTO);
        return new ResponseEntity<>(shop, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable Long id, @RequestBody ShopDTO shopDTO) {
        shopDTO.setId(id);
        Shop shop = shopService.updateShop(shopDTO);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable Long id) {
        Shop shop = shopService.getShopById(id);
        if (shop == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Shop> getShopBySellerId(@PathVariable Long sellerId) {
        Shop shop = shopService.getShopBySellerId(sellerId);
        if (shop == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }
}
