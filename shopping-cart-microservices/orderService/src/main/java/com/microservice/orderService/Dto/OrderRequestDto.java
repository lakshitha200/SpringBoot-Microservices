package com.microservice.orderService.Dto;



import com.microservice.orderService.Entity.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequestDto {

    private Long customerId;
    private Double totalPrice;
    private List<OrderItem> orderItems;

    // Getters and Setters
}

