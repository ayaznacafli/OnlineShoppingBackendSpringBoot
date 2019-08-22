package com.ayaz.onlineshopping.service.impl;

import com.ayaz.onlineshopping.model.Address;
import com.ayaz.onlineshopping.repository.AddressRepository;
import com.ayaz.onlineshopping.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address getAddress(int addressId) {
       return addressRepository.findById(addressId).get();
    }

    @Override
    public Address getBillingAddress(int userId) {
        return (Address) addressRepository.findShippingAddresses(userId,true);
    }

    @Override
    public List<Address> listShippingAddresses(int userId) {
        return addressRepository.findBillingAddress(userId,true);
    }
    @Override
    public boolean addAddress(Address address) {
        try {
            addressRepository.save(address);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
