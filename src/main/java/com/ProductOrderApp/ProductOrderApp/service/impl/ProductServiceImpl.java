package com.ProductOrderApp.ProductOrderApp.service.impl;

import com.ProductOrderApp.ProductOrderApp.exceptions.BadRequestException;
import com.ProductOrderApp.ProductOrderApp.exceptions.ResourceNotFoundException;
import com.ProductOrderApp.ProductOrderApp.model.Product;
import com.ProductOrderApp.ProductOrderApp.repository.CartItemRepository;
import com.ProductOrderApp.ProductOrderApp.repository.OrderItemRepository;
import com.ProductOrderApp.ProductOrderApp.repository.ProductRepository;
import com.ProductOrderApp.ProductOrderApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));
        existingProduct.setName(product.getName());
        existingProduct.setType(product.getType());
        existingProduct.setPrice(product.getPrice());
        return productRepository.save(existingProduct);
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (orderItemRepository.existsByProductId(id)) {
            throw new BadRequestException("Cannot delete product — it is used in an order.");
        }
        else if (cartItemRepository.existsByProductId(id)) {
            throw new BadRequestException("Cannot delete product — it is used in a cart.");
        }
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
