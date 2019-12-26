package com.example.Sales.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OrderLineItem {

    @Id
    private Long id;

    //list of item_names are got from the order()
    private String itemName;
//    private int quantity;

//    @OneToOne
//    @JoinColumn
    private Long orderId;

    public OrderLineItem() {
    }

}
