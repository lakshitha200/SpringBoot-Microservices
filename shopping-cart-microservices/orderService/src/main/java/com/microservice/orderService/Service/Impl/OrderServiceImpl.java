package com.microservice.orderService.Service.Impl;


import com.microservice.orderService.Config.PaymentAPIClient;
import com.microservice.orderService.Config.ProductAPIClient;
import com.microservice.orderService.Dto.OrderRequestDto;
import com.microservice.orderService.Dto.OrderResponseDto;
import com.microservice.orderService.Dto.PaymentDto;
import com.microservice.orderService.Dto.ProductDto;
import com.microservice.orderService.Entity.Order;
import com.microservice.orderService.Entity.OrderStatus;
import com.microservice.orderService.Repository.OrderRepository;
import com.microservice.orderService.Service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private ProductAPIClient productClient;
    private PaymentAPIClient paymentClient;

    private final String INSTANCE_NAME = "ORDER-SERVICE";

    private static final String TOPIC = "micro1-order-service-t1";

    private KafkaTemplate<String, Object> kafkaTemplate;

    //create order

    @CircuitBreaker(name = INSTANCE_NAME, fallbackMethod = "productServiceFallback")
//    @Retry(name = INSTANCE_NAME,fallbackMethod = "productServiceFallback")
    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        System.out.println("OrderController");
        System.out.println(TOPIC);
        List<String> productIDList = new ArrayList<>();
        orderRequestDto.getOrderItems().stream()
                .forEach(product->productIDList.add(product.getProductId()));

        // Check product availability
        if (!productClient.checkProductAvailability(productIDList)) {
            throw new RuntimeException("Product not available");
        }
        System.out.println("Product Available Can do Payment");

        // Create order
        Order order = new Order();
        modelMapper.map(orderRequestDto,order);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        Order neworder = orderRepository.save(order);


        // Process payment
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setOrderId(neworder.getId());
        paymentDto.setAmount(neworder.getTotalPrice());

        boolean paymentSuccessful = paymentClient.makePayment(paymentDto);

        if (paymentSuccessful) {
            // kafka call pending status
            String message = "Your order with ID "+ neworder.getId() +" has been placed successfully and is currently "+OrderStatus.PENDING.toString().toLowerCase()+". We will notify you once it's processed.";
            sendMessageToKafkaTopic(message);
            return modelMapper.map(orderRepository.save(neworder),OrderResponseDto.class);
        }
        return null;
    }

    public void sendMessageToKafkaTopic(String message){
        System.out.println("This is working");
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent Message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "] with partition=["
                        + result.getRecordMetadata().partition() + "]"
                );
            }
            else{
                System.out.println("Sent Message=[" + message +
                        "] due to " + ex.getMessage());
            }
        });
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(
                (order)-> modelMapper.map(order,OrderResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderByID(Long id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found"));

        return modelMapper.map(existingOrder, OrderResponseDto.class);
    }

    // update status "CONFIRMED" / "CANCELLED" / "DELIVERED" / "SHIPPED"
    @Override
    public String updateOrderStatus(Long id, OrderStatus status) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found"));
        existingOrder.setStatus(status);
        Order savedOrder = orderRepository.save(existingOrder);

        // kafka call status
        if(savedOrder.getStatus()==OrderStatus.CONFIRMED){
            String message = "Great news! Your order with ID "+ savedOrder.getId() +" has been confirmed. We are preparing it for shipment.";
            sendMessageToKafkaTopic(message);
        } else if (savedOrder.getStatus()==OrderStatus.CANCELLED) {
            String message ="Your order with ID "+ savedOrder.getId() +" has been cancelled. If this was a mistake, please contact customer support for assistance";
            sendMessageToKafkaTopic(message);
        } else if (savedOrder.getStatus()==OrderStatus.SHIPPED) {
            String message = "Your order with ID "+ savedOrder.getId() +" has been shipped! You can expect delivery soon. Track your shipment for updates.";
            sendMessageToKafkaTopic(message);
        } else if (savedOrder.getStatus()==OrderStatus.DELIVERED)  {
            String message = "Your order with ID "+ savedOrder.getId() + " has been successfully delivered! We hope you enjoy your purchase.";
            sendMessageToKafkaTopic(message);
        }

        return "Order Status Changed to "+status;
    }

    // Fallback method if product service is down
    public OrderResponseDto productServiceFallback(OrderRequestDto orderRequestDto, Throwable t) {
        // Fallback logic (e.g., return a default response, cache, or alternative handling)
        Order fallbackOrder = new Order();
        fallbackOrder.setStatus(OrderStatus.FAILED);

        // kafka call fail status
        String message = "Order "+OrderStatus.FAILED.toString().toLowerCase()+"! We're sorry, but your order could not be processed due to a failure. Please check your payment details and try again.";
        sendMessageToKafkaTopic(message);

        // Additional fallback processing
        return modelMapper.map(fallbackOrder,OrderResponseDto.class);
    }

}
