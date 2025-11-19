package com.example.ecommerce.serviceImpl.lockingMethodology.optimistic;

import com.example.ecommerce.entity.Inventory;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.InventoryRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Service("optimisticOrderService")
@RequiredArgsConstructor
public class OptimisticOrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final TransactionTemplate transactionTemplate;


    private static final int MAX_RETRIES = 3;


    @Override
    public Order placeOrder(Long userId, Long productId, int quantity) {

        int attempt = 0;

        while (attempt < MAX_RETRIES) {
            try {
                return transactionTemplate.execute(status -> {
                    // business logic inside actual transaction
                    return doPlaceOrder(userId, productId, quantity);
                });

            } catch (ObjectOptimisticLockingFailureException e) {
                attempt++;
                log.warn("Optimistic lock attempt {} failed for product {}: {}", attempt, productId, e.getMessage());
                if (attempt == MAX_RETRIES) {
                    log.error("Max retry reached for product {}.", productId);
                    throw new IllegalStateException("High traffic, please try again.");
                }
                waitBeforeRetry(attempt);
            }
        }
        throw new IllegalStateException("Unable to place order");
    }

    private Order doPlaceOrder(Long userId, Long productId, int quantity) {

        productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Inventory inventory = inventoryRepository
                .findByProductId(productId)
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
        order.setOrderStatus("PROCESSING");

        return orderRepository.save(order);
    }

    private void waitBeforeRetry(int attempt) {
        try {
            // exponential backoff with jitter: base 100ms times 2^(attempt-1) plus up to 50ms random
            long base = 100L * (1L << (attempt - 1));
            long jitter = (long) (Math.random() * 50);
            Thread.sleep(Math.min(base + jitter, 2000L));
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
