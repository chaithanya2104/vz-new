package com.example.Sales.controller;

import com.example.Sales.exception.OrderCannotBeCreated;
import com.example.Sales.exception.OrdersNotFoundException;
import com.example.Sales.model.*;
import com.example.Sales.service.CustomerServiceProxy;
import com.example.Sales.service.ItemServiceProxy;
import com.example.Sales.service.OrderLineItemService;
import com.example.Sales.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sales-api")
public class SalesOrderController {


    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @Autowired
    private CustomerServiceProxy customerServiceProxy;

    @Autowired
    private ItemServiceProxy itemServiceProxy;




    @GetMapping("/orders/notUsing/{email}")
    public List<HashMap<String,Integer>> getOrderByEmail(@PathVariable("email") String email){

        HashMap<String, Integer> hmap = new HashMap<>();
        List<HashMap<String,Integer>> finalList = new ArrayList<>();
        List<SalesOrder> orderIds = salesOrderService.getOrderByEmail(email);
        for (SalesOrder salesOrder: orderIds) {
            hmap = this.orderLineItemService.getOrdersById(salesOrder.getId());
            finalList.add(hmap);
        }
        if (finalList.isEmpty()){
          throw new OrdersNotFoundException("Orders for "+ email + "not found");
        }
        return finalList;

    }

    @PostMapping("/add")
    public Long createNewOrder (@RequestBody CreateOrder createOrder){
        System.out.println("hit the endpoint...");
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
                System.out.println("Item Validation passes");
                existingItemsList.add(item);
                price=price+itemServiceProxy.getItemByName(item).getPrice();
            }
            else{
                System.out.println("order cannot be created. Item validation Failed");
                throw new OrderCannotBeCreated("Items not present.This Order Cannot be Created");
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
            throw new OrderCannotBeCreated("This Order Cannot be Created");
        }
        return id;
    }


    @GetMapping("/orders/{email}")
    public List<Object> getOrderListByEmail(@PathVariable("email") String email){
        List<SalesOrder> salesOrders= this.salesOrderService.getOrderByEmail(email);

        //retrieve items for each orderId
        return salesOrders.stream().map(salesOrder ->{
        List<OrderLineItem> orderLineItems=this.orderLineItemService.getOrderLineItemsByOrderId(salesOrder.getId());
                    List<String> itemNames = new ArrayList<>();
                    orderLineItems.forEach(item -> {
                        for(int i=0 ; i<item.getQuantity() ; i++) {
                            itemNames.add(item.getItemName());
                        }
                    });
                    CreateOrder returnOrder = new CreateOrder();
                    returnOrder.setEmail(salesOrder.getEmail());
                    returnOrder.setDate(salesOrder.getDate());
                    returnOrder.setItems(itemNames);
                    returnOrder.setDescription(salesOrder.getDescription());
                    return returnOrder;
        }).collect(Collectors.toList());

    }
}


