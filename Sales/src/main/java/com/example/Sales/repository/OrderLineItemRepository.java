package com.example.Sales.repository;

import com.example.Sales.model.OrderLineItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderLineItemRepository extends CrudRepository<OrderLineItem,Long> {
    @Override
    Iterable<OrderLineItem> findAll();
    @Override
    Optional<OrderLineItem> findById(Long aLong);

    Optional<OrderLineItem> findByOrderId(Long order_id);


}
