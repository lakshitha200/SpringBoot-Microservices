package com.microservice.paymentService.Controller;


import com.microservice.paymentService.Dto.PaymentDto;
import com.microservice.paymentService.Entity.Payment;
import com.microservice.paymentService.Entity.PaymentStatus;
import com.microservice.paymentService.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments(){
        return new ResponseEntity<>(paymentService.getAllPayments(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getAPaymentById(@PathVariable Long id){
        return new ResponseEntity<>(paymentService.getAPaymentById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> createPayment(@RequestBody PaymentDto paymentDto) {
        return new ResponseEntity<>(paymentService.createPayment(paymentDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long id, @RequestParam PaymentStatus status) {
        return new ResponseEntity<>(paymentService.updatePaymentStatus(id, status),HttpStatus.OK);
    }
}

