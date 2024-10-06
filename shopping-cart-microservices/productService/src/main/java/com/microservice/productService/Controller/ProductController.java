package com.microservice.productService.Controller;

import com.microservice.productService.Dto.ProductRequestDto;
import com.microservice.productService.Dto.ProductResponseDto;
import com.microservice.productService.Entity.Product;
import com.microservice.productService.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private Environment environment;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<String>(productService.createProduct(productRequestDto),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<String>(productService.updateProduct(id, productRequestDto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }

    @PostMapping("/check-availability")
    public Boolean checkProductAvailability(@RequestBody List<String> productIDList) {
        System.out.println(environment.getProperty("server.port"));
        return productService.checkProductAvailability(productIDList);
    }
}
