package com.example.ecommerce.service;

import com.example.ecommerce.entity.Inventory;
import com.example.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    Page<Inventory> getAll(Pageable pageable);

    Inventory getById(Long id);

    Inventory getByProductId(Long productId);

    Inventory addNewStocks(Inventory inventory);

    Inventory updateStock(Long id, Inventory inventory);
}
