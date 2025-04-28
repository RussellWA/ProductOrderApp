package com.ProductOrderApp.ProductOrderApp.repository;

import com.ProductOrderApp.ProductOrderApp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
