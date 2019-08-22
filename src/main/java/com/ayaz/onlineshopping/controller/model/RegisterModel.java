package com.ayaz.onlineshopping.controller.model;

import com.ayaz.onlineshopping.model.Address;
import com.ayaz.onlineshopping.model.User;

import java.io.Serializable;

public class RegisterModel implements Serializable {

    private User user;
    private Address billing;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getBilling() {
        return billing;
    }

    public void setBilling(Address billing) {
        this.billing = billing;
    }
}
