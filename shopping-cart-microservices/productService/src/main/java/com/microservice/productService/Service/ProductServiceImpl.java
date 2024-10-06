package com.microservice.productService.Service;

import com.microservice.productService.Dto.ProductRequestDto;
import com.microservice.productService.Dto.ProductResponseDto;
import com.microservice.productService.Entity.Product;
import com.microservice.productService.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private ModelMapper modelMapper;


    // get all products
    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
         return productList.stream()
                .map((product)-> modelMapper.map(product,ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    // get product by id
    @Override
    public ProductResponseDto getProductById(String id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found"));

        return modelMapper.map(existingProduct, ProductResponseDto.class);
    }

    // create new product
    @Override
    public String createProduct(ProductRequestDto productRequestDto) {
        productRepository.save(modelMapper.map(productRequestDto,Product.class));
        return "New Product Added.";
    }

    // update product
    @Override
    public String updateProduct(String id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setQuantity(productRequestDto.getQuantity());
        product.setImg(productRequestDto.getImg());
        return "Product with ID " + id + " updated successfully!";
    }

    // delete product
    @Override
    public String deleteProduct(String id) {
        productRepository.deleteById(id);
        return "Product with ID " + id + " deleted successfully!";
    }

    // check Product Availability
    @Override
    public Boolean checkProductAvailability(List<String> productIDList) {
        productIDList.stream().forEach(product->{
            Product existingProduct = productRepository.findById(product)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if(existingProduct.getQuantity() > 0){
                System.out.println("Product Id: "+product+ " available");
            }else {
                throw new RuntimeException("Product Id: "+product+ " Not available");
            }
        });
        return true;
    }
}
