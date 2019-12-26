package com.example.Item.controller;

import com.example.Item.model.Items;
import com.example.Item.service.ItemService;
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

}
