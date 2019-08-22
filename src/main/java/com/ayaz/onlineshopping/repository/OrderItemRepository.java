package com.ayaz.onlineshopping.repository;

import com.ayaz.onlineshopping.model.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository  extends CrudRepository<OrderItem,Integer> {

}
