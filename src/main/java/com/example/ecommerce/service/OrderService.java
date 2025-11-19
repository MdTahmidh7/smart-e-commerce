package com.example.ecommerce.service;

import com.example.ecommerce.entity.Order;

public interface OrderService {
    Order placeOrder(Long userId, Long productId, int quantity);
}
