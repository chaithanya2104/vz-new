package com.example.customers.service;


import com.example.customers.model.Customers;
import com.example.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    //create new Customer
    public Customers add(Customers customers){
        System.out.println("added...");
        return customerRepository.save(customers);
    }

    //getAll
    public List<Customers> getAll(){
        System.out.println("i think i got them all...");
        return customerRepository.findAll();
    }

    //getCustomerByEmail
    public Customers getCustomerByEmail(String email){
        Optional <Customers> customerWithEmail = customerRepository.findByEmail(email);
        if(customerWithEmail.isPresent()){
            System.out.println("got it...");
            return customerWithEmail.get();
        }
        return null;
    }

}
