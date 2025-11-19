package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.entity.Inventory;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.enumuration.OrderStatus;
import com.example.ecommerce.repository.InventoryRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.OrderService;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order placeOrder(Long userId, Long productId, int quantity) {
        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Inventory inventory = inventoryRepository.findByProductIdWithLock(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (inventory.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setStockQuantity(inventory.getStockQuantity() - quantity);
        inventoryRepository.save(inventory);

        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setOrderStatus(OrderStatus.PROCESSING.name());
        return orderRepository.save(order);
    }
}
