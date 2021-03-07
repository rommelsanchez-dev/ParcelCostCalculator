package com.mynt.parcel_cost_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ParcelCostCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelCostCalculatorApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}




}
