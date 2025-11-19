package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.entity.Inventory;
import com.example.ecommerce.repository.InventoryRepository;
import com.example.ecommerce.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public Page<Inventory> getAll(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public Inventory getById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
    }

    @Override
    public Inventory getByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found with product id: " + productId));
    }

    @Override
    public Inventory addNewStocks(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateStock(Long id, Inventory inventory) {
        Inventory existingInventory = getById(id);
        existingInventory.setStockQuantity(inventory.getStockQuantity());
        return inventoryRepository.save(existingInventory);
    }
}
