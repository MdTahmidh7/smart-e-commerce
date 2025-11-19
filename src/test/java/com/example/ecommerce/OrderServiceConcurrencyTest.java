package com.example.ecommerce;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.enumuration.UserType;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class OrderServiceConcurrencyTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testConcurrentOrderPlacement_ShouldSucceedForAvailableStockOnly() throws InterruptedException {
        // Given: A product with a stock of 2
        ProductDto createdProduct = createProductWithStock("Test Product", 2);
        
        // Find the product ID from the repository as the service returns a DTO
        long productId = productService.getAllProducts(org.springframework.data.domain.Pageable.unpaged())
                                    .getContent().stream()
                                    .filter(p -> p.getProductName().equals("Test Product"))
                                    .findFirst().get().getId();

        // Given: 3 users who want to buy the product
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            users.add(createUser("user" + i, "123456789" + i));
        }

        // When: 3 concurrent users try to place an order for 1 item each
        int numThreads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        AtomicInteger successfulOrders = new AtomicInteger(0);
        AtomicInteger failedOrders = new AtomicInteger(0);

        for (User user : users) {
            executor.submit(() -> {
                try {
                    latch.countDown();
                    latch.await(); // Synchronize threads to start at the same time
                    orderService.placeOrder(user.getId(), productId, 1);
                    successfulOrders.incrementAndGet();
                } catch (Exception e) {
                    failedOrders.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Then:
        assertEquals(2, successfulOrders.get(), "Exactly 2 orders should be successful.");
        assertEquals(1, failedOrders.get(), "Exactly 1 order should fail due to insufficient stock.");
    }

    private ProductDto createProductWithStock(String name, int stock) {
        ProductDto productDto = new ProductDto();
        productDto.setProductName(name);
        productDto.setDescription("A test product");
        productDto.setPrice(10.0);
        productDto.setQuantity(stock);
        productDto.setImageUrl("http://example.com/image.jpg");
        return productService.createProduct(productDto);
    }

    private User createUser(String name, String phone) {
        User user = User.builder()
                .name(name)
                .phoneNumber(phone)
                .password(passwordEncoder.encode("password"))
                .userType(UserType.USER)
                .build();
        return userRepository.save(user);
    }
}
