package com.ayaz.onlineshopping.repository;

import com.ayaz.onlineshopping.model.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {


    @Query("FROM Address WHERE userId = :userId AND shipping = :isShipping ORDER BY id DESC")
    public List<Address> findShippingAddresses(@Param("userId") int userId, @Param("isShipping") boolean isShipping);


    @Query("FROM Address WHERE userId = :userId AND billing = :isBilling")
    public List<Address> findBillingAddress(@Param("userId") int userId, boolean isBilling);
}
