package com.ayaz.onlineshopping.service;

import com.ayaz.onlineshopping.model.Cart;
import com.ayaz.onlineshopping.model.CartLine;
import com.ayaz.onlineshopping.model.OrderDetail;

import java.util.List;

public interface CartLineService {

    public List<CartLine> list(int cartId);
    public CartLine get(int id);
    public boolean add(CartLine cartLine);
    public boolean update(CartLine cartLine);
    public boolean remove(CartLine cartLine);
    public CartLine getByCartAndProduct(int cartId, int productId);
    boolean updateCart(Cart cart);
    public List<CartLine> listAvailable(int cartId);
    boolean addOrderDetail(OrderDetail orderDetail);
}
