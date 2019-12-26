package com.example.Sales.service;

import com.example.Sales.model.Items;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="item-service", url="localhost:8081")
public interface ItemServiceProxy {
    //getItemByName
    @GetMapping("/items/{name}")
    Items getItemByName(@PathVariable String name);

    //getAll
    @GetMapping("/items")
    List<Items> getAllItems();
}
