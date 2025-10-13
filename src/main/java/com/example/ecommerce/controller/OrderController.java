package com.example.ecommerce.controller;

import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @PostMapping("/place")
    public Object createOrder(@RequestBody Object order) {
        return null;
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
