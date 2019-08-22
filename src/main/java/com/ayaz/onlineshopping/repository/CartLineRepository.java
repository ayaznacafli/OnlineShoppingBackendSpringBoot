package com.ayaz.onlineshopping.repository;

import com.ayaz.onlineshopping.model.CartLine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartLineRepository extends CrudRepository<CartLine,Integer> {

    @Query("SELECT c FROM CartLine c WHERE c.cartId = :cartId")
    List<CartLine> findAllByCartId(@Param("cartId") int cartId);

    @Query("SELECT c FROM CartLine c WHERE c.cartId=:cartId AND c.product.id = :productId")
    CartLine findByCartAndProduct(@Param("cartId")int cartId, @Param("productId")int productId);

    @Query("FROM CartLine WHERE cartId=:cartId AND aviable=:aviable")
    List<CartLine> findAllAvailable(@Param("cartId")int cartId, @Param("aviable") boolean aviable);
}
