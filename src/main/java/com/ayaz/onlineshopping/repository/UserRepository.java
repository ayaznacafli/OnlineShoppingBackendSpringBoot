package com.ayaz.onlineshopping.repository;

import com.ayaz.onlineshopping.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    User findByEmail(String email);

}