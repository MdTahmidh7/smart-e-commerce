package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Payment;
import com.example.ecommerce.entity.enumuration.OrderStatus;
import com.example.ecommerce.entity.enumuration.PaymentStatus;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.PaymentRepository;
import com.example.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Payment generatePaymentUrl(Order order) {
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getQuantity() * getProductPrice(order.getProductId())); // Assuming a function to get price
        payment.setPaymentStatus(PaymentStatus.PENDING.name());
        payment.setPaymentUrl("https://example.com/pay/" + UUID.randomUUID());
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        order.setOrderStatus(OrderStatus.DONE.name());
        payment.setPaymentStatus(PaymentStatus.COMPLETED.name());

        orderRepository.save(order);
        paymentRepository.save(payment);
    }

    private double getProductPrice(Long productId) {
        // In a real application, you would fetch the product's price from the database.
        // For this simulation, we'll use a fixed price.
        return 10.0;
    }
}
