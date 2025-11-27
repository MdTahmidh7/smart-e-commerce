package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Inventory;
import com.example.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Inventory> addNewStocks(@RequestBody Inventory inventory) {
        Inventory newInventory = inventoryService.addNewStocks(inventory);
        return new ResponseEntity<>(newInventory, HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<Page<Inventory>> viewStocks(Pageable pageable) {
        Page<Inventory> inventoryPage = inventoryService.getAll(pageable);
        return ResponseEntity.ok(inventoryPage);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Inventory> viewStocksById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Inventory> updateStock(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.updateStock(id, inventory);
        return ResponseEntity.ok(updatedInventory);
    }
}
