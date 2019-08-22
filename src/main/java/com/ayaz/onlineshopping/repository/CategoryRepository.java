package com.ayaz.onlineshopping.repository;

import com.ayaz.onlineshopping.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {

}
