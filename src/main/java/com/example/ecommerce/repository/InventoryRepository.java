package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Inventory;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "5000"),  // lock timeout prevent deadlock
            @QueryHint(name = "org.hibernate.fetchSize", value = "1"),
            @QueryHint(name = "org.hibernate.timeout", value = "5000")
    })
    @Query("""
           SELECT i
           FROM Inventory i
           WHERE i.productId = :productId
           """)
    Optional<Inventory> findByProductIdWithLock(@Param("productId") Long productId);
}
