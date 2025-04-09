package com.ethann.springshop.repository;

import com.ethann.springshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findByOrderNumber(String orderNumber);
}