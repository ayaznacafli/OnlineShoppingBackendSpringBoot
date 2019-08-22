
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.ayaz.onlineshopping.controller.model.CheckoutModel;
import com.ayaz.onlineshopping.controller.model.UserModel;
import com.ayaz.onlineshopping.model.*;
import com.ayaz.onlineshopping.service.AddressService;
import com.ayaz.onlineshopping.service.CartLineService;
import com.ayaz.onlineshopping.service.ProductService;
import com.ayaz.onlineshopping.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CheckoutHandler {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutHandler.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartLineService cartLineService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private HttpSession session;


    public CheckoutModel init(String name) throws Exception {

        User user = userService.getByEmail(name);
        CheckoutModel checkoutModel = null;

        if (user != null) {
            checkoutModel = new CheckoutModel();
            checkoutModel.setUser(user);
            checkoutModel.setCart(user.getCart());

            double checkoutTotal = 0.0;
            List<CartLine> cartLines = cartLineService.listAvailable(user.getCart().getId());

            if (cartLines.size() == 0) {
                throw new Exception("There are no products available for checkout now!");
            }

            for (CartLine cartLine : cartLines) {
                checkoutTotal += cartLine.getTotal();
            }

            checkoutModel.setCartLines(cartLines);
            checkoutModel.setCheckoutTotal(checkoutTotal);
        }

        return checkoutModel;
    }


    public List<Address> getShippingAddresses(CheckoutModel model) {

        List<Address> addresses = addressService.listShippingAddresses(model.getUser().getId());

        if (addresses.size() == 0) {
            addresses = new ArrayList<>();
        }

        addresses.add(addresses.size(), addressService.getBillingAddress(model.getUser().getId()));

        return addresses;

    }

    public String saveAddressSelection(CheckoutModel checkoutModel, int shippingId) {
        String transitionValue = "success";
        //logger.info(String.valueOf(shippingId));
        Address shipping = addressService.getAddress(shippingId);
        checkoutModel.setShipping(shipping);
        return transitionValue;
    }


    public String saveAddress(CheckoutModel checkoutModel, Address shipping) {
        String transitionValue = "success";
        shipping.setUserId(checkoutModel.getUser().getId());
        shipping.setShipping(true);
        addressService.addAddress(shipping);
        checkoutModel.setShipping(shipping);
        return transitionValue;
    }


    public String saveOrder(CheckoutModel checkoutModel) {
        String transitionValue = "success";
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUser(checkoutModel.getUser());

        orderDetail.setShipping(checkoutModel.getShipping());

        Address billing = addressService.getBillingAddress(checkoutModel.getUser().getId());
        orderDetail.setBilling(billing);

        List<CartLine> cartLines = checkoutModel.getCartLines();
        OrderItem orderItem = null;

        double orderTotal = 0.0;
        int orderCount = 0;
        Product product = null;

        for (CartLine cartLine : cartLines) {

            orderItem = new OrderItem();

            orderItem.setBuyingPrice(cartLine.getBuyingPrice());
            orderItem.setProduct(cartLine.getProduct());
            orderItem.setProductCount(cartLine.getProductCount());
            orderItem.setTotal(cartLine.getTotal());

            orderItem.setOrderDetail(orderDetail);
            orderDetail.getOrderItems().add(orderItem);

            orderTotal += orderItem.getTotal();
            orderCount++;

            product = cartLine.getProduct();
            product.setQuantity(product.getQuantity() - cartLine.getProductCount());
            product.setPurchases(product.getPurchases() + cartLine.getProductCount());
            productService.update(product);

            cartLineService.remove(cartLine);


        }

        orderDetail.setOrderTotal(orderTotal);
        orderDetail.setOrderCount(orderCount);
        orderDetail.setOrderDate(new Date());

        // save the order
        cartLineService.addOrderDetail(orderDetail);

        // set it to the orderDetails of checkoutModel
        checkoutModel.setOrderDetail(orderDetail);


        // update the cart
        Cart cart = checkoutModel.getCart();
        cart.setGrandTotal(cart.getGrandTotal() - orderTotal);
        cart.setCartLines(cart.getCartLines() - orderCount);
        cartLineService.updateCart(cart);

        // update the cart if its in the session
        UserModel userModel = (UserModel) session.getAttribute("userModel");
        if (userModel != null) {
            userModel.setCart(cart);
        }


        return transitionValue;
    }


    public OrderDetail getOrderDetail(CheckoutModel checkoutModel) {
        return checkoutModel.getOrderDetail();
    }

}