package com.microservice.paymentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.paymentService.Dto.PaymentDto;
import com.microservice.paymentService.Entity.Payment;
import com.microservice.paymentService.Entity.PaymentStatus;
import com.microservice.paymentService.Repository.PaymentRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Date;
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
class PaymentServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ModelMapper modelMapper;

	PaymentDto paymentDto;

	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);
	}

	@BeforeAll
	static void beforeAll() {
		mySQLContainer.start();
	}

	@AfterAll
	static void afterAll() {
		mySQLContainer.stop();
	}


	@BeforeEach
	public void setup() {
		paymentDto = PaymentDto.builder()
				.id(1L)
				.orderId(20L)
				.amount(300_000.00)
				.status(PaymentStatus.COMPLETED)
				.paymentDate(new Date())
				.build();

		System.out.println(mySQLContainer.getDatabaseName());
		System.out.println(mySQLContainer.getJdbcUrl());
		System.out.println(mySQLContainer.getUsername());
		System.out.println(mySQLContainer.getPassword());
	}

	//Make Payment Test
	@Test
	@Order(1)
	public void savePaymentAPITest() throws Exception {
		paymentRepository.save(modelMapper.map(paymentDto,Payment.class));
		mockMvc.perform(post("/api/payments")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(paymentDto)))

				.andExpect(status().isCreated())
				.andDo(print());
	}


	//Get All Payment Test
	@Test
	@Order(2)
	public void getAllPaymentsAPITest() throws Exception {
		// precondition
		Payment payment = modelMapper.map(paymentDto, Payment.class);
		Payment payment1 = modelMapper.map(PaymentDto.builder()
				.id(2L)
				.orderId(21L)
				.amount(500_000.00)
				.status(PaymentStatus.COMPLETED)
				.paymentDate(new Date())
				.build(), Payment.class);

		List<Payment> paymentList = new ArrayList<>();
		paymentList.add(payment);
		paymentList.add(payment1);

		paymentRepository.saveAll(paymentList);

		// Action and Verify
		mockMvc.perform(get("/api/payments"))

				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()", is(paymentList.size())));

	}

	//Get paymentById Test
	@Test
	@Order(3)
	public void getPaymentById() throws Exception {

		// Action and Verify
		mockMvc.perform(get("/api/payments/{id}", paymentDto.getId()))

				.andExpect(status().isOk())
				.andDo(print());
	}
}
