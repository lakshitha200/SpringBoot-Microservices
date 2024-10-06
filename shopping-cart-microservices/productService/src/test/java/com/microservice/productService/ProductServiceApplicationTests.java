package com.microservice.productService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.productService.Dto.ProductRequestDto;
import com.microservice.productService.Entity.Product;
import com.microservice.productService.Repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ModelMapper modelMapper;

	ProductRequestDto productRequestDto;

	@Container
	final static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);

	}

	@BeforeAll
	static void beforeAll() {
		mongoDBContainer.start();
	}

	@AfterAll
	static void afterAll() {
		mongoDBContainer.stop();
	}


	@BeforeEach
	public void setup(){
		productRequestDto = ProductRequestDto.builder()
				.id("66ff737f4882e")
				.name("Laptop")
				.description("This is an Hp Laptop")
				.price(300_000.00)
				.quantity(15)
				.build();

		System.out.println(mongoDBContainer.getReplicaSetUrl());
	}

	// save product test
	@Test
	@Order(1)
	public void saveProductAPITest() throws Exception {
		mockMvc.perform(post("/api/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productRequestDto)))

				.andExpect(status().isCreated())
				.andDo(print());

	}

	//Get All products Test
	@Test
	@Order(2)
	public void getAllProductAPITest() throws Exception {
		// precondition
		Product product = modelMapper.map(productRequestDto,Product.class);
		Product product2 = modelMapper.map(ProductRequestDto.builder()
				.id("77ff737f4882e")
				.name("I Phone")
				.description("This is an I Phone")
				.price(200_000.00)
				.quantity(20)
				.build(),Product.class);

		List<Product> productList = new ArrayList<>();
		productList.add(product);
		productList.add(product2);
		productRepository.saveAll(productList);
		// Action and Verify
		mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()",is(productList.size())));

	}

	//Get product by id Test
	@Test
	@Order(3)
	public void getProductByIdAPITest() throws Exception{

		// Action and Verify
		mockMvc.perform(get("/api/products/{id}", productRequestDto.getId()))

				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.name", is(productRequestDto.getName())));

	}

	//Update Product Test
	@Test
	@Order(4)
	public void updateProductAPITest() throws Exception {

		//Action
		productRequestDto.setName("Dell Laptop");
		mockMvc.perform(put("/api/products/{id}", productRequestDto.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productRequestDto)))
				//verify
				.andExpect(status().isOk())
				.andDo(print());
		System.out.println(productRequestDto);
	}

	//Delete Product Test
	@Test
	@Order(5)
	public void deleteProductAPITest() throws Exception{

		// Action and Verify
		mockMvc.perform(delete("/api/products/{id}", productRequestDto.getId()))
				.andExpect(status().isOk())
				.andDo(print());
	}
}


