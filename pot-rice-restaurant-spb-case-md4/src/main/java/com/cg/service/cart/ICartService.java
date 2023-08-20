package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.cart.CartItemReqDTO;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface ICartService extends IGeneralService<Cart, Long> {

    Cart addToCart(CartItemReqDTO cartItemReqDTO, Product product, User user);
    Optional<Cart> findByUser(User user);
}
