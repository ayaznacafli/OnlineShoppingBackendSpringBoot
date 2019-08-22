package com.ayaz.onlineshopping.service;

import com.ayaz.onlineshopping.model.Address;

import java.util.List;

public interface AddressService {
    Address getAddress(int addressId);
    Address getBillingAddress(int userId);
    List<Address> listShippingAddresses(int userId);
    boolean addAddress(Address address);
}
