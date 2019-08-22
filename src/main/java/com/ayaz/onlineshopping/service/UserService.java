package com.ayaz.onlineshopping.service;

import com.ayaz.onlineshopping.model.Address;
import com.ayaz.onlineshopping.model.User;

import java.util.List;

public interface UserService {

    User getByEmail(String email);
    User get(int id);
    boolean add(User user);
    boolean updateAddress(Address address);
}
