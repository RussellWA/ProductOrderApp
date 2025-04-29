package com.ProductOrderApp.ProductOrderApp.repository;

import com.ProductOrderApp.ProductOrderApp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    boolean existsByProductId(Long productId);
}
