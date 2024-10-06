package com.microservice.paymentService.Repository;

import com.microservice.paymentService.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

