package com.ayaz.onlineshopping.service.impl;

import com.ayaz.onlineshopping.model.Cart;
import com.ayaz.onlineshopping.model.CartLine;
import com.ayaz.onlineshopping.model.OrderDetail;
import com.ayaz.onlineshopping.repository.CartLineRepository;
import com.ayaz.onlineshopping.repository.CartRepository;
import com.ayaz.onlineshopping.repository.OrderDetailRepository;
import com.ayaz.onlineshopping.service.CartLineService;
import com.ayaz.onlineshopping.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartLineServiceImpl implements CartLineService {
    @Autowired
    private CartLineRepository cartLineRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    public List<CartLine> list(int cartId) {
        return cartLineRepository.findAllByCartId(cartId);
    }

    @Override
    public CartLine get(int id) {
        return cartLineRepository.findById(id).get();
    }

    @Override
    public boolean add(CartLine cartLine) {
        try {
            cartLineRepository.save(cartLine);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(CartLine cartLine) {
        try {
            cartLineRepository.save(cartLine);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(CartLine cartLine) {
        try {
            cartLineRepository.delete(cartLine);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CartLine getByCartAndProduct(int cartId, int productId) {
        return cartLineRepository.findByCartAndProduct(cartId,productId);
    }

    @Override
    public boolean updateCart(Cart cart) {
        try {
            cartRepository.save(cart);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CartLine> listAvailable(int cartId) {
        return cartLineRepository.findAllAvailable(cartId,true);
    }

    @Override
    public boolean addOrderDetail(OrderDetail orderDetail) {
        try {
            orderDetailRepository.save(orderDetail);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
