package com.example.Sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.example.Sales")
//@EnableFeignClients("com.example")
@SpringBootApplication
public class SalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesApplication.class, args);
	}

}