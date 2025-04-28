package com.ProductOrderApp.ProductOrderApp.service.impl;

import com.ProductOrderApp.ProductOrderApp.DTO.ProductToCartRequest;
import com.ProductOrderApp.ProductOrderApp.model.*;
import com.ProductOrderApp.ProductOrderApp.repository.CartRepository;
import com.ProductOrderApp.ProductOrderApp.repository.OrderRepository;
import com.ProductOrderApp.ProductOrderApp.repository.ProductRepository;
import com.ProductOrderApp.ProductOrderApp.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @Override
    public Cart addProductToCart(Long cartId, ProductToCartRequest request) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> previousCartItem = cart.getItems().stream().filter(cartItem -> cartItem.getProduct().equals(product)).findFirst();

        if (previousCartItem.isPresent()) {
            CartItem existingCartitem = previousCartItem.get();
            existingCartitem.setQuantity(existingCartitem.getQuantity() + request.getQuantity());
            existingCartitem.setTotalPrice(existingCartitem.getProduct().getPrice() * existingCartitem.getQuantity());
        }
        else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            item.setTotalPrice(product.getPrice() * request.getQuantity());

            cart.getItems().add(item);
        }

        cartRepository.save(cart);

        return cart;
    }

    @Override
    public Cart viewCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Transactional
    @Override
    public Order placeOrder(Long cartId, String customerName) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Long totalPrice = 0L;

        if (cart.getItems().isEmpty()) throw new RuntimeException("Cart is Empty");

        Order order = new Order();
        order.setCustomerName(customerName);

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());

            totalPrice += cartItem.getTotalPrice();

            order.getItems().add(orderItem);
        }

        order.setPrice(totalPrice);

        orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return order;
    }

}
