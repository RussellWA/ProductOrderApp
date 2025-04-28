package com.ProductOrderApp.ProductOrderApp.controller;

import com.ProductOrderApp.ProductOrderApp.DTO.ProductToCartRequest;
import com.ProductOrderApp.ProductOrderApp.model.Cart;
import com.ProductOrderApp.ProductOrderApp.model.Order;
import com.ProductOrderApp.ProductOrderApp.repository.CartRepository;
import com.ProductOrderApp.ProductOrderApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/{cartId}/add")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Long cartId, @RequestBody ProductToCartRequest request) {
        Cart cart = cartService.addProductToCart(cartId, request);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public Cart viewCart(@PathVariable Long cartId) {
        return cartService.viewCart(cartId);
    }

    @PostMapping("/{cartId}/place-order")
    public ResponseEntity<Order> placeOrder(@PathVariable Long cartId, @RequestParam String customerName) {
        Order order = cartService.placeOrder(cartId, customerName);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/create")
    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }
}
