package com.ayaz.onlineshopping.controller.handler;


import com.ayaz.onlineshopping.controller.model.RegisterModel;
import com.ayaz.onlineshopping.model.Address;
import com.ayaz.onlineshopping.model.Cart;
import com.ayaz.onlineshopping.model.User;
import com.ayaz.onlineshopping.service.AddressService;
import com.ayaz.onlineshopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterHandler {



    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;


    public RegisterModel init() {
        return new RegisterModel();
    }
    public void addUser(RegisterModel registerModel, User user) {
        registerModel.setUser(user);
    }
    public void addBilling(RegisterModel registerModel, Address billing) {
        registerModel.setBilling(billing);
    }

    public String validateUser(User user, MessageContext error) {
        String transitionValue = "success";
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            error.addMessage(new MessageBuilder().error().source(
                    "confirmPassword").defaultText("Password does not match confirm password!").build());
            transitionValue = "failure";
        }
        if(userService.getByEmail(user.getEmail())!=null) {
            error.addMessage(new MessageBuilder().error().source(
                    "email").defaultText("Email address is already taken!").build());
            transitionValue = "failure";
        }
        return transitionValue;
    }

    public String saveAll(RegisterModel registerModel) {
        String transitionValue = "success";
        User user = registerModel.getUser();
        if(user.getRole().equals("USER")) {
            Cart cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);

        Address billing = registerModel.getBilling();
        billing.setUserId(user.getId());
        billing.setBilling(true);
        addressService.addAddress(billing);
        return transitionValue ;
    }
}
