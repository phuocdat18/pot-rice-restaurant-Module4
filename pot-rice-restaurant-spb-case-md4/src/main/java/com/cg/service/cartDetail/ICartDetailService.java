package com.cg.service.cartDetail;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.model.dto.cart.CartDetailDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICartDetailService extends IGeneralService<CartDetail, Long> {
    boolean existsCartDetailByCart(Cart cart);
    CartDetail findCartDetailsByProductAndCart (Product product, Cart cart);

    List<CartDetailDTO> findAllCartDetailDTO(Long id);
    List<CartDetail> findCartDetailsByCart(Cart cart);
}