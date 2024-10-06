package com.microservice.productService.Service;

import com.microservice.productService.Dto.ProductRequestDto;
import com.microservice.productService.Dto.ProductResponseDto;
import com.microservice.productService.Entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(String id);

    String createProduct(ProductRequestDto productRequestDto);

    Boolean checkProductAvailability(List<String> productIDList);

    String updateProduct(String id, ProductRequestDto productRequestDto);

    String deleteProduct(String id);
}

