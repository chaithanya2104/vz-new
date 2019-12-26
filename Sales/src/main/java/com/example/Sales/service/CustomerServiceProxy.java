package com.example.Sales.service;

import com.example.Sales.model.Customers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="customer-service", url="localhost:8080")
public interface CustomerServiceProxy {

    @GetMapping("/customers/{email}")
    Customers getCustomerByEmail(@PathVariable("email") String email);

    @GetMapping("/customers")
    List<Customers> getAllCustomers();
}
