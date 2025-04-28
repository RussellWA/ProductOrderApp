package com.ProductOrderApp.ProductOrderApp.service;

import com.ProductOrderApp.ProductOrderApp.DTO.ProductToCartRequest;
import com.ProductOrderApp.ProductOrderApp.model.Cart;
import com.ProductOrderApp.ProductOrderApp.model.Order;

public interface CartService {
    Cart addProductToCart(Long cartId, ProductToCartRequest request);
    Cart viewCart(Long cartId);
    Order placeOrder(Long cartId, String customerName);
}
