package com.microservice.orderService.Config;

import com.microservice.orderService.Dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "PRODUCT-SERVICE", path = "/api/products")
public interface ProductAPIClient {

    @GetMapping()
    List<ProductDto> getAllProducts();
    @PostMapping("/check-availability")
    Boolean checkProductAvailability(@RequestBody List<String> productIDList);
}
