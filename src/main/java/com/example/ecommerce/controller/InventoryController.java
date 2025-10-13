package com.example.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @PostMapping("/add")
    public ResponseEntity<Object> addNewStocks(@RequestBody Object stock) {
        return null;
    }

    @GetMapping("/view")
    public ResponseEntity<Object> viewStocks() {
        return null;
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Object> viewStocksById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<Object> updateStock(@PathVariable Long id) {
        return null;
    }
}
