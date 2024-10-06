package com.microservice.orderService.Service;

import com.microservice.orderService.Dto.OrderRequestDto;
import com.microservice.orderService.Dto.OrderResponseDto;
import com.microservice.orderService.Entity.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderByID(Long id);

    String updateOrderStatus(Long id, OrderStatus status);
}

