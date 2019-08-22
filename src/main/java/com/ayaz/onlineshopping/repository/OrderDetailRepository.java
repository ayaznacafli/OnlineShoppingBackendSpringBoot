package com.ayaz.onlineshopping.repository;

import com.ayaz.onlineshopping.model.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail,Integer> {


}
