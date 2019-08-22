package com.ayaz.onlineshopping.controller.service;

import com.ayaz.onlineshopping.controller.model.UserModel;
import com.ayaz.onlineshopping.model.Cart;
import com.ayaz.onlineshopping.model.CartLine;
import com.ayaz.onlineshopping.model.Product;
import com.ayaz.onlineshopping.service.CartLineService;
import com.ayaz.onlineshopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service("cartService")
public class CartService {

    @Autowired
    private CartLineService cartLineService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpSession session;


    private Cart getCart(){
        return ((UserModel)session.getAttribute("userModel")).getCart();
    }

    public List<CartLine> getCartLines(){
        return cartLineService.list(getCart().getId());
    }

    public String manageCartLine(int cartLineId, int count){
        CartLine cartLine = cartLineService.get(cartLineId);
        double oldTotal = cartLine.getTotal();
        Product product = cartLine.getProduct();

        if(product.getQuantity() < count){
            return "result=unavailable";
        }

        cartLine.setProductCount(count);
        cartLine.setBuyingPrice(product.getUnitPrice());
        cartLine.setTotal(product.getUnitPrice()*count);
        cartLineService.update(cartLine);

        Cart cart = this.getCart();
        cart.setGrandTotal(cart.getGrandTotal() - oldTotal + cartLine.getTotal());
        cartLineService.updateCart(cart);
        return "result=updated";
    }
    public String addCartLine(int productId) {
        Cart cart = this.getCart();
        String response = null;
        CartLine cartLine = cartLineService.getByCartAndProduct(cart.getId(), productId);
        if(cartLine==null) {
            cartLine = new CartLine();
            Product product = productService.get(productId);
            cartLine.setCartId(cart.getId());
            cartLine.setProduct(product);
            cartLine.setProductCount(1);
            cartLine.setBuyingPrice(product.getUnitPrice());
            cartLine.setTotal(product.getUnitPrice());
            cartLineService.add(cartLine);

            cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
            cart.setCartLines(cart.getCartLines() + 1);
            cartLineService.updateCart(cart);

            response = "result=added";
        }
        else {
            if(cartLine.getProductCount() < 3) {
                response = this.manageCartLine(cartLine.getId(), cartLine.getProductCount() + 1);
            }
            else {
                response = "result=maximum";
            }
        }
        return response;
    }

    public String removeCartLine(int cartLineId) {
        CartLine cartLine = cartLineService.get(cartLineId);
        Cart cart = this.getCart();
        cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());
        cart.setCartLines(cart.getCartLines() - 1);
        cartLineService.updateCart(cart);
        cartLineService.remove(cartLine);
        return "result=deleted";
    }


    public String validateCartLine() {
        Cart cart = this.getCart();
        List<CartLine> cartLines = cartLineService.list(cart.getId());
        double grandTotal = 0.0;
        int lineCount = 0;
        String response = "result=success";
        boolean changed = false;
        Product product = null;
        for(CartLine cartLine : cartLines) {
            product = cartLine.getProduct();
            changed = false;
            if((!product.isActive() && product.getQuantity() == 0) && cartLine.isAvailable()) {
                cartLine.setAvailable(false);
                changed = true;
            }
            if((product.isActive() && product.getQuantity() > 0) && !(cartLine.isAvailable())) {
                cartLine.setAvailable(true);
                changed = true;
            }
            if(cartLine.getBuyingPrice() != product.getUnitPrice()) {
                cartLine.setBuyingPrice(product.getUnitPrice());
                cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
                changed = true;
            }
            if(cartLine.getProductCount() > product.getQuantity()) {
                cartLine.setProductCount(product.getQuantity());
                cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
                changed = true;
            }
            if(changed) {
                cartLineService.update(cartLine);
                response = "result=modified";
            }
            grandTotal += cartLine.getTotal();
            lineCount++;
        }
        cart.setCartLines(lineCount++);
        cart.setGrandTotal(grandTotal);
        cartLineService.updateCart(cart);
        return response;
    }


}
