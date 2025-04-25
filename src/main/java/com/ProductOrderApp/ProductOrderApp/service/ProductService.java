package com.ProductOrderApp.ProductOrderApp.service;

import com.ProductOrderApp.ProductOrderApp.model.Product;
import org.springframework.data.domain.Page;


public interface ProductService {
    Product createProduct(Product product);
    Page<Product> getAllProducts(int page, int size);
    Product getProductById(Long id);
    Product updateProduct(Long id, Product product);
    boolean deleteProduct(Long id);
}
