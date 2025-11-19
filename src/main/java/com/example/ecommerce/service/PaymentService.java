package com.example.ecommerce.service;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Payment;

public interface PaymentService {
    Payment generatePaymentUrl(Order order);
    void processPayment(Long orderId);
}
