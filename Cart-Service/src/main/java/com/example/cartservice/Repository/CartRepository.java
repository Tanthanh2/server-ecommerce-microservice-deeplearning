package com.example.cartservice.Repository;

import com.example.cartservice.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByIdCustomer(Long idCustomer);
}
