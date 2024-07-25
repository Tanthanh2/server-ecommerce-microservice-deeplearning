package com.example.cartservice.controller;

import com.example.cartservice.Dto.CartItemDTO;
import com.example.cartservice.Entity.Cart;
import com.example.cartservice.Common.ResponseObject;
import com.example.cartservice.Service.CartImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    @Autowired
    CartImpl cart;

    @GetMapping("/test")
    public String testApi() {
        return "Cart Service API is working!";
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject<List<Cart>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject<>("ok", "Query All Success", cart.findAll())
        );
    }

    @PostMapping("/{idCustomer}")
    public ResponseEntity<ResponseObject<Cart>> addCart(@PathVariable("idCustomer") Long idCustomer,
                                                        @RequestBody @Valid CartItemDTO cartItemDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject<>("ok", "Success Add Cart", cart.addCart(idCustomer, cartItemDTO))
        );
    }

    @PutMapping("/{idCart}/{idCartItem}")
    public ResponseEntity<ResponseObject<Cart>> addCart(@PathVariable("idCart") Long idCart,
                                                        @PathVariable("idCartItem") Long idCartItem) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject<>("ok", "Success Delete Cart", cart.deleteCartItem(idCart, idCartItem))
        );
    }
}
