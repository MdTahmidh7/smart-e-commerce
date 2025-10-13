package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @PostMapping("/add")
    public Object addToCart(@RequestBody List<Object> items) {
        return null;
    }

    @PostMapping("/remove")
    public Object removeFromCart(Object item) {
        return null;
    }

}
