package com.ayaz.onlineshopping.service.impl;

import com.ayaz.onlineshopping.model.Address;
import com.ayaz.onlineshopping.model.User;
import com.ayaz.onlineshopping.repository.AddressRepository;
import com.ayaz.onlineshopping.repository.UserRepository;
import com.ayaz.onlineshopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User get(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean add(User user) {
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateAddress(Address address) {
        try {
            addressRepository.save(address);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
