package com.microservice.paymentService.Dto;

import com.microservice.paymentService.Entity.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;
    private Long orderId;
    private Double amount;
    private PaymentStatus status;
    private Date paymentDate;
}
