package com.example.Item.service;

import com.example.Item.exception.ItemNotFoundException;
import com.example.Item.model.Items;
import com.example.Item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    //addItem
    public Items add(Items item){
       return itemRepository.save(item);
    }

    //getAll()
    public List<Items> getAll(){
        return itemRepository.findAll();
    }

    //getItemByName(String name)
    public Items getItemByName(String name){
        Optional<Items> items = itemRepository.findByName(name);
        if(items.isPresent())
            return items.get();
        else{
            throw new ItemNotFoundException("Customer not found for item_name: "+ name);
        }

    }
//    public void getPriceByName(String name){
//        System.out.println(itemRepository.findPriceByName(name));
////        Double price = itemRepository.findPriceByName(name);
////        return price;
//    }

}
