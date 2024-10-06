package com.microservice.paymentService.Service;


import com.microservice.paymentService.Dto.PaymentDto;
import com.microservice.paymentService.Entity.Payment;
import com.microservice.paymentService.Entity.PaymentStatus;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> getAllPayments();
    PaymentDto getAPaymentById(Long id);

    boolean createPayment(PaymentDto paymentDto);

    Payment updatePaymentStatus(Long id, PaymentStatus status);



}

