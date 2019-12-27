package com.example.Sales.controller;

import com.example.Sales.model.*;
import com.example.Sales.service.CustomerServiceProxy;
import com.example.Sales.service.ItemServiceProxy;
import com.example.Sales.service.OrderLineItemService;
import com.example.Sales.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SalesOrderController {


    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @Autowired
    private CustomerServiceProxy customerServiceProxy;

    @Autowired
    private ItemServiceProxy itemServiceProxy;

    //CreateOrder from addNewOrder
    @PostMapping("/CreateNewOrder")
    public Long createNewOrder (@RequestBody CreateOrder createOrder){
        Long id= null;
        Double price=0.00;
        List<String> existingItemsList= new ArrayList<String>();
        System.out.println("existingItemsList: "+existingItemsList);

        System.out.println("createNewOrder");

        //to validate customer
        Customers customer =customerServiceProxy.getCustomerByEmail(createOrder.getEmail());
        System.out.println(customer);

        //Validating items and adding the existing items to existingItemList

        for(String item: createOrder.getItems()){
            Items itemForValidation =itemServiceProxy.getItemByName(item);
            if (itemForValidation!=null){
                existingItemsList.add(item);
                price=price+itemServiceProxy.getItemByName(item).getPrice();
            }
        }

        System.out.println("existingItems after validation: " + existingItemsList );

        //checking if customer and items exist
        //if exists then send modifiedCreteOrder to create order
        if (customer != null && !(existingItemsList.isEmpty())) {
            System.out.println("Customer is available");
            CreateOrder modifiedCreateOrder = new CreateOrder(createOrder.getDescription(), createOrder.getDate(), createOrder.getEmail(), existingItemsList);
            System.out.println(modifiedCreateOrder);
            id = salesOrderService.addNewOrder(modifiedCreateOrder,price);
        }
        else {
            System.out.println("Customer is not existing or items not existing");
        }
        return id;
    }

//Get Order By email
//    @GetMapping("/orders/{email}")
//    public Map<List<SalesOrder>,> getOrderByEmail(@PathVariable("email") String email){
//        System.out.println("get order by email");
//        List<SalesOrder> ordersFromEmail = salesOrderService.getOrderByEmail(email);
//        if (!(ordersFromEmail.isEmpty())){
//            return ordersFromEmail;
//
//        }
//        else{
//            System.out.println("Matching orders not found");
//            return null;
//        }
//    }

    @GetMapping("/orders/{email}")
    public List<HashMap<String,Integer>> getOrderByEmail(@PathVariable("email") String email){

        HashMap<String, Integer> hmap = new HashMap<>();
        List<HashMap<String,Integer>> finalList = new ArrayList<>();
        List<SalesOrder> orderIds = salesOrderService.getOrderByEmail(email);
        for (SalesOrder salesOrder: orderIds) {
            hmap = this.orderLineItemService.getOrdersById(salesOrder.getId());
            finalList.add(hmap);
        }
        return finalList;

    }
}
