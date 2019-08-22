package com.ayaz.onlineshopping.repository;

import com.ayaz.onlineshopping.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {

 //   @Query("FROM Product WHERE active = true ORDER BY \" + param + \" DESC")
 //   List<Product> findProductsByParam(String param, int count);

    @Query("FROM Product WHERE active = :active")
    List<Product> findActiveProducts(@Param("active") boolean active);

    @Query("FROM Product WHERE active = :active AND categoryId = :categoryId")
    List<Product> findActiveProductsByCategory(@Param("active") boolean active, @Param("categoryId") int categoryId);

//    @Query("FROM Product WHERE active = :active ORDER BY id")
//    List<Product> findtLatestActiveProducts(int count);
}
