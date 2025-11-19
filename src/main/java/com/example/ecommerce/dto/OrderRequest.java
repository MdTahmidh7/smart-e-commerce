package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
