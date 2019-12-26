package com.example.Sales.controller;

import com.example.Sales.model.CreateOrder;
import com.example.Sales.service.CustomerServiceProxy;
import com.example.Sales.service.ItemServiceProxy;
import com.example.Sales.model.Customers;
import com.example.Sales.model.SalesOrder;
import com.example.Sales.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SalesOrderController {


    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private CustomerServiceProxy customerServiceProxy;

    @Autowired
    private ItemServiceProxy itemServiceProxy;

//    public SalesOrderController(SalesOrderService salesOrderService, CustomerServiceProxy customerServiceProxy, ItemServiceProxy itemServiceProxy ){
//        this.salesOrderService=salesOrderService;
//        this.customerServiceProxy=customerServiceProxy;
//        this.itemServiceProxy=itemServiceProxy;
//    }

//    public SalesOrderController(){
//        System.out.println("Reached sales order");
//    }
    //System.out.println("Reached sales order");

    //CreateOrder
    @PostMapping("/CreateOrder")
    public Long createOrder(@RequestBody SalesOrder order)
    {
//        boolean customerEmailAvailable = false;
        Long id = null;
        System.out.println("create order");
        System.out.println(order);
        System.out.println(order.getDate()+order.getEmail()+order.getDescription()+order.getPrice());

        //1. Validating customer by checking emailId from CustomerService
        Customers customer =customerServiceProxy.getCustomerByEmail(order.getEmail());
//        if (customer != null) {customerEmailAvailable = true;}

        if (customer != null) {
            System.out.println("Customer is available");
            id = salesOrderService.addOrder(order);
        }
        else {
            System.out.println("Customer is not existing");
        }
       return id;
    }

    //CreateOrder2 from addNewOrder
    @PostMapping("/CreateNewOrder")
    public Long createNewOrder (@RequestBody CreateOrder createOrder){
        Long id= null;
        System.out.println("createNewOrder");
        Customers customer =customerServiceProxy.getCustomerByEmail(createOrder.getEmail());
        if (customer != null) {
            System.out.println("Customer is available");
            id = salesOrderService.addNewOrder(createOrder);
        }
        else {
            System.out.println("Customer is not existing");
        }
        return id;
    }



//Get Order By email
    @GetMapping("/orders/{email}")
    public SalesOrder getOrderByEmail(@PathVariable("email") String email){
        System.out.println("get order by email");
        return salesOrderService.getOrderByEmail(email);
        //System.out.printf("getOrderByEmail email = "+ email);
        //Optional<SalesOrder> orderByEmail= salesOrderService.getOrderByEmail("cahidt@hmail.com");
//        if (orderByEmail.isPresent()){
//
//        };

    }
}
