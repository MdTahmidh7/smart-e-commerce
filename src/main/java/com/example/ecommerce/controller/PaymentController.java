package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @GetMapping("/payment-url")
    public Object getPaymentUrl(@RequestBody Object order) {
        return null;
    }

    @GetMapping("/status")
    public Object getPaymentStatus(@RequestBody Object payment) {
        return null;
    }

}
