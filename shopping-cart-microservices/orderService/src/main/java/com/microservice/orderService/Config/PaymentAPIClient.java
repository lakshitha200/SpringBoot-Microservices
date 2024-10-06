package com.microservice.orderService.Config;

import com.microservice.orderService.Dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "PAYMENT-SERVICE", path = "/api/payments")
public interface PaymentAPIClient {

        @PostMapping
        boolean makePayment(@RequestBody PaymentDto paymentDto);

}
