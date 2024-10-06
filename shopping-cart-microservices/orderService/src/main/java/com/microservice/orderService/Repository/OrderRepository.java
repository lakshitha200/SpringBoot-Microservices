package com.microservice.orderService.Repository;

import com.microservice.orderService.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

