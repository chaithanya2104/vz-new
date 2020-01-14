package com.example.Sales.service;

import com.example.Sales.model.CreateOrder;
import com.example.Sales.model.Customers;
import com.example.Sales.model.Items;
import com.example.Sales.model.OrderLineItem;
import com.example.Sales.repository.OrderLineItemRepository;
import com.example.Sales.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderLineItemService {

    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    @Autowired
    private ItemServiceProxy itemServiceProxy;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    public void addItems(List<String> items, Long orderId){
        String itemName=null;
        System.out.println("addItems to OrderLineItem");
        //for each item in items
        for(String item:items) {
            System.out.println(item);
            OrderLineItem orderLineItem = new OrderLineItem();
            if ((orderLineItemRepository.getOrderLineItemByItemNameAndOrderId(item,orderId)).isEmpty())
            {
            orderLineItem.setItemName(item);
            orderLineItem.setOrderId(orderId);
            orderLineItem.setQuantity(Collections.frequency(items, item));
            orderLineItemRepository.save(orderLineItem);
             }
            System.out.println("order ID from orderLineItem " + orderLineItem.getOrderId());
        }
    }

    public HashMap<String, Integer> getOrdersById(Long id)
    {
        HashMap<String, Integer> hmap = new HashMap<>();
        List<OrderLineItem> orderListFromTable = this.orderLineItemRepository.getOrderLineItemsByOrderId(id);
        System.out.println(orderListFromTable);
        for (OrderLineItem order: orderListFromTable) {
            hmap.put(order.getItemName(),order.getQuantity());
        }

        return hmap;
    }

    public List<OrderLineItem> getOrderLineItemsByOrderId(Long orderId) {
        List<OrderLineItem> orderItems = orderLineItemRepository.getOrderLineItemsByOrderId(orderId);
        return orderItems;
    }




}
