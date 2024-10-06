package com.microservice.paymentService.Service;

import com.microservice.paymentService.Dto.PaymentDto;
import com.microservice.paymentService.Entity.Payment;
import com.microservice.paymentService.Entity.PaymentStatus;
import com.microservice.paymentService.Repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private PaymentRepository paymentRepository;
    private ModelMapper modelMapper;


    @Override
    public List<PaymentDto> getAllPayments() {
        List<Payment> paymentList = paymentRepository.findAll();
        return paymentList.stream()
                .map((payment)-> modelMapper.map(payment,PaymentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto getAPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return modelMapper.map(payment,PaymentDto.class);
    }

    @Override
    public boolean createPayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setOrderId(paymentDto.getOrderId());
        payment.setAmount(paymentDto.getAmount());
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(new Date());
        paymentRepository.save(payment);
        return true;
    }

    @Override
    public Payment updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}

