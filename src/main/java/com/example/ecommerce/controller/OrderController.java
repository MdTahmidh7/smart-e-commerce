package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Payment;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService optimisticOrderService;
    private final OrderService pessimisticOrderService;
    private final PaymentService paymentService;

    public OrderController(
            @Qualifier("optimisticOrderService") OrderService optimisticOrderService,
            @Qualifier("pessimisticOrderService") OrderService pessimisticOrderService,
            PaymentService paymentService
    ) {
        this.optimisticOrderService = optimisticOrderService;
        this.pessimisticOrderService = pessimisticOrderService;
        this.paymentService = paymentService;
    }

    @PostMapping("/place")
    public ResponseEntity<Payment> createOrder(
            @RequestBody OrderRequest orderRequest,
            @RequestParam(defaultValue = "pessimistic") String locking
    ) {
        try {
            Order order;
            if ("optimistic".equalsIgnoreCase(locking)) {
                order = optimisticOrderService.placeOrder(
                        orderRequest.getUserId(),
                        orderRequest.getProductId(),
                        orderRequest.getQuantity()
                );
            } else {
                order = pessimisticOrderService.placeOrder(
                        orderRequest.getUserId(),
                        orderRequest.getProductId(),
                        orderRequest.getQuantity()
                );
            }
            Payment payment = paymentService.generatePaymentUrl(order);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/update")
    public Object updateOrder(@RequestBody Object order) {
        return null;
    }

    @PostMapping("/cancel/{id}")
    public Object cancelOrder(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/view")
    public Object viewOrders() {
        return null;
    }

    @GetMapping("/view/{id}")
    public Object viewOrderById(@PathVariable Long id) {
        return null;
    }


}
