package com.microservice.productService.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("products")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String img;

}

