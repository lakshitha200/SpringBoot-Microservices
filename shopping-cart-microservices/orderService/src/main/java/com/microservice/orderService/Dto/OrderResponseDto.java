package com.microservice.orderService.Dto;

import com.microservice.orderService.Entity.OrderItem;
import com.microservice.orderService.Entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long id;
    private Long customerId;
    private Date orderDate;
    private Double totalPrice;
    private OrderStatus status;
    private List<OrderItem> orderItems;
}
