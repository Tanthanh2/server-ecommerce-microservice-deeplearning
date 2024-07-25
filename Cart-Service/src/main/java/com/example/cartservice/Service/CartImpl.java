package com.example.cartservice.Service;

import com.example.cartservice.Dto.CartItemDTO;
import com.example.cartservice.Entity.Cart;
import com.example.cartservice.Entity.CartItem;
import com.example.cartservice.Repository.CartItemRepository;
import com.example.cartservice.Repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartImpl {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper mapper;

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart addCart(Long idCustomer, CartItemDTO cartItemDTO) {
        // If user have no cart
        List<Cart> isFound = cartRepository.findByIdCustomer(idCustomer);

        //Convert
        CartItem cartItem = mapper.map(cartItemDTO, CartItem.class);

        if (isFound.isEmpty()) {
            Cart cart = new Cart();
            cart.setIdCustomer(idCustomer);

            List<CartItem> cartItems = new ArrayList<>();
            cartItem.setCart(cart);
            cartItems.add(cartItem);
            cart.setCartItems(cartItems);

            return cartRepository.save(cart);
        }

        // If user have Cart
        List<CartItem> userCart = isFound.get(0).getCartItems();
        boolean isAdded = false;
        for (CartItem item : userCart) {
            // with the same id product
            if(Objects.equals(item.getIdProduct(), cartItem.getIdProduct())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                isAdded = true;
                break;
            }
        }

        // without the same id product
        if(!isAdded) userCart.add(cartItem);

        return cartRepository.save(isFound.get(0));
    }

    public Cart deleteCartItem(Long idCart, Long idCartItem) {
        Optional<Cart> cart = cartRepository.findById(idCart);
        if (cart.isPresent()) {
            // Check Exist idCartItem
            if(!cartItemRepository.existsById(idCartItem)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find CartItem with id = " + idCartItem);

            cart.get().getCartItems().removeIf(cartItem -> cartItem.getId().equals(idCartItem));
            cartItemRepository.deleteById(idCartItem);
            return cartRepository.save(cart.get());
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find Cart with id = " + idCart);
    }
}
