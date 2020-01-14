package com.example.Item.controller;

import com.example.Item.model.Items;
import com.example.Item.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    //addItem
    @PostMapping("/items")
    public Items add(Items items){
        return itemService.add(items);
    }

    //getAll
    @GetMapping("/items")
    public List<Items> getAllItems(){
        return itemService.getAll();
    }


    //getItemByName
    @GetMapping("/items/{name}")
    public Items getItemByName(@PathVariable String name){
        return itemService.getItemByName(name);
    }

//    @GetMapping("/itemsPrice/{name}")
//    public Double getPriceByName(@PathVariable String name){
//            return getItemByName(name).getPrice();
//    }

    @GetMapping("/items/hystrix-fault-tolerance")
    @HystrixCommand(fallbackMethod = "faultToleranceFallbackMethod")
    public Items testFaultTolerance(){
        System.out.println("abhi toh party shuru hui hai!!!");
        throw  new RuntimeException(" there was an error in execution");
    }

    public Items faultToleranceFallbackMethod(){
        System.out.println("hey something went wrong in the original method so we are here in fallback...");
        Items dummyObject = new Items("dummy name", 99.99, "dummydescription");
        return dummyObject;
    }

}
