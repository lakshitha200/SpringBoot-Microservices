package com.microservice.productService.Repository;

import com.microservice.productService.Entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
