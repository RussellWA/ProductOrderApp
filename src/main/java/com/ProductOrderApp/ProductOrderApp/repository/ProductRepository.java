package com.ProductOrderApp.ProductOrderApp.repository;

import com.ProductOrderApp.ProductOrderApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
