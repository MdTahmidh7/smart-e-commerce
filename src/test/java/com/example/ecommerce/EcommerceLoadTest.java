package com.example.ecommerce;

import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EcommerceLoadTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final int NUM_USERS = 400;
    private static final long PRODUCT_ID = 1L;
    private static final int QUANTITY_PER_USER = 2;

    @Data
    private static class AuthResponse {
        private String jwtToken;
        private Long userId;
    }

    @Test
    public void testConcurrentOrderPlacement() throws Exception {
        AtomicInteger successfulOrders = new AtomicInteger(0);
        AtomicInteger failedOrders = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USERS);
        CountDownLatch latch = new CountDownLatch(NUM_USERS);

        for (int i = 0; i < NUM_USERS; i++) {
            executorService.submit(() -> {
                try {
                    AuthResponse authResponse = registerUserAndGetToken();
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + authResponse.getJwtToken());
                    headers.set("Content-Type", "application/json");

                    OrderRequest orderRequest = new OrderRequest();
                    orderRequest.setUserId(authResponse.getUserId());
                    orderRequest.setProductId(PRODUCT_ID);
                    orderRequest.setQuantity(QUANTITY_PER_USER);

                    HttpEntity<OrderRequest> requestEntity = new HttpEntity<>(orderRequest, headers);

                    ResponseEntity<String> response = restTemplate.exchange(
                            "http://localhost:" + port + "/api/v1/orders/place",
                            HttpMethod.POST,
                            requestEntity,
                            String.class
                    );

                    if (response.getStatusCode().is2xxSuccessful()) {
                        successfulOrders.incrementAndGet();
                    } else {
                        failedOrders.incrementAndGet();
                    }
                } catch (Exception e) {
                    failedOrders.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        System.out.println("Successful orders: " + successfulOrders.get());
        System.out.println("Failed orders: " + failedOrders.get());

        assertEquals(50, successfulOrders.get());
        assertEquals(350, failedOrders.get());
    }

    private AuthResponse registerUserAndGetToken() {
        String uniquePhone = "1" + String.format("%09d", new Random().nextInt(1000000000));
        RegisterRequest registerRequest = new RegisterRequest("user", uniquePhone, "password");

        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/auth/register",
                registerRequest,
                AuthResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to register user: " + response.getBody());
        }
    }
}