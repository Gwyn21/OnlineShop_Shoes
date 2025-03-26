package com.kickzHub.kickzHub.repository;

import com.kickzHub.kickzHub.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByShippingAddressId(Long shippingAddressId);
}
