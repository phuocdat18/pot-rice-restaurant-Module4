package com.cg.service.cartDetail;


import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.model.dto.cart.CartDetailDTO;
import com.cg.repository.CartDetailRepository;
import com.cg.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartDetailServiceImpl implements ICartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<CartDetail> findAll() {
        return cartDetailRepository.findAll();
    }

    @Override
    public Optional<CartDetail> findById(Long id) {
        return cartDetailRepository.findById(id);
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public void delete(CartDetail cartDetail) {
        cartDetailRepository.delete(cartDetail);
        List<CartDetail> cartDetails = cartDetailRepository.findAll();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartDetail cartDetail1 : cartDetails) {
            totalAmount.add(cartDetail1.getAmount());
        }
        Cart cart = cartDetail.getCart();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) {
        cartDetailRepository.deleteById(id);
    }

    @Override
    public boolean existsCartDetailByCart(Cart cart) {
        return cartDetailRepository.existsCartDetailByCart(cart);
    }

    @Override
    public CartDetail findCartDetailsByProductAndCart(Product product, Cart cart) {
        return cartDetailRepository.findCartDetailsByProductAndCart(product, cart);
    }

    @Override
    public List<CartDetailDTO> findAllCartDetailDTO(Long id) {
        return cartDetailRepository.findAllCartDetailDTO(id);
    }

    @Override
    public List<CartDetail> findCartDetailsByCart(Cart cart) {
        return cartDetailRepository.findCartDetailsByCart(cart);
    }


}