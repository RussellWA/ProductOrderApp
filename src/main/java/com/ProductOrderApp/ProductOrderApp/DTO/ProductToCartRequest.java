package com.ProductOrderApp.ProductOrderApp.DTO;

import lombok.Data;

@Data
public class ProductToCartRequest {
    private Long productId;
    private int quantity;
}
