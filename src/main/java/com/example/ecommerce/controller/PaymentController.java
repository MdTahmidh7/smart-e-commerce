package com.example.ecommerce.controller;

import com.example.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process/{orderId}")
    public ResponseEntity<String> processPayment(@PathVariable Long orderId) {
        try {
            paymentService.processPayment(orderId);
            return ResponseEntity.ok("Payment processed successfully for order " + orderId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to process payment: " + e.getMessage());
        }
    }
}
