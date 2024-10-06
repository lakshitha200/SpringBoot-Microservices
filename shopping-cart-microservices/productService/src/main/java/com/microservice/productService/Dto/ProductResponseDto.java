package com.microservice.productService.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {

    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String img;
}
