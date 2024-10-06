package com.microservice.orderService.Controller;

import com.microservice.orderService.Dto.OrderRequestDto;
import com.microservice.orderService.Dto.OrderResponseDto;
import com.microservice.orderService.Entity.OrderStatus;
import com.microservice.orderService.Service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        System.out.println("OrderController");
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto getOrderByID(@PathVariable Long id) {
        return orderService.getOrderByID(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return orderService.updateOrderStatus(id, status);
    }


}

