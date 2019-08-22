package com.ayaz.onlineshopping.controller.model;

import com.ayaz.onlineshopping.model.Cart;

import java.io.Serializable;

public class UserModel implements Serializable {

    private int id;
    private String fullName;
    private String role;

    private Cart cart;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
