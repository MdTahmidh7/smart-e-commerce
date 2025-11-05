package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Inventory;
import com.example.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<Inventory> addNewStocks(@RequestBody Inventory inventory) {
        return null;
    }

    @GetMapping("/view")
    public ResponseEntity<Inventory> viewStocks() {
        return null;
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Inventory> viewStocksById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<Inventory> updateStock(@PathVariable Long id) {
        return null;
    }
}
