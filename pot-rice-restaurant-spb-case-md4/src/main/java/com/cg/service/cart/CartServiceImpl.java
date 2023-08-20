package com.cg.service.cart;


import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.cart.CartItemReqDTO;
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
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cart addToCart(CartItemReqDTO cartItemReqDTO, Product product, User user) {

        Optional<Cart> cartOptional = cartRepository.findByUser(user);

        if (cartOptional.isEmpty()) {
            Cart cartNew = new Cart();
            cartNew.setUser(user);
            cartNew.setTotalAmount(BigDecimal.ZERO);
            cartRepository.save(cartNew);

            BigDecimal price = product.getPrice();
            long quantity = cartItemReqDTO.getQuantity();
            BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));


            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cartNew);
            cartDetail.setProduct(product);
            cartDetail.setTitle(product.getTitle());
            cartDetail.setPrice(product.getPrice());
            cartDetail.setUnit(product.getUnit());
            cartDetail.setQuantity(cartItemReqDTO.getQuantity());
            cartDetail.setAmount(amount);
            cartDetailRepository.save(cartDetail);

            cartNew.setTotalAmount(amount);
            return cartRepository.save(cartNew);
        } else {
            if(cartDetailRepository.existsCartDetailByCart(cartOptional.get())) {
                CartDetail cartDetail = cartDetailRepository.findCartDetailsByProductAndCart(product, cartOptional.get());
                if (cartDetail != null) {
                    Cart cart = cartOptional.get();

                    BigDecimal price = product.getPrice();
                    long quantity = cartDetail.getQuantity() +  cartItemReqDTO.getQuantity();
                    BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));


                    cartDetail.setQuantity(quantity);
                    cartDetail.setAmount(amount);

                    BigDecimal totalAmount = cart.getTotalAmount().add(amount);
                    cart.setTotalAmount(totalAmount);
                    return cartRepository.save(cart);
                }
                else {

                    BigDecimal price = product.getPrice();
                    long quantity = cartItemReqDTO.getQuantity();
                    BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));

                    Cart cart = cartOptional.get();
                    CartDetail cartDetailNew = new CartDetail();
                    cartDetailNew.setCart(cart);
                    cartDetailNew.setProduct(product);
                    cartDetailNew.setTitle(product.getTitle());
                    cartDetailNew.setPrice(product.getPrice());
                    cartDetailNew.setUnit(product.getUnit());
                    cartDetailNew.setQuantity(cartItemReqDTO.getQuantity());
                    cartDetailNew.setAmount(amount);
                    cartDetailRepository.save(cartDetailNew);

                    BigDecimal totalAmount = cart.getTotalAmount().add(amount);
                    cart.setTotalAmount(totalAmount);
                    return cartRepository.save(cart);
                }
            }else {

                BigDecimal price = product.getPrice();
                long quantity = cartItemReqDTO.getQuantity();
                BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));

                Cart cart = cartOptional.get();
                CartDetail cartDetailNew = new CartDetail();
                cartDetailNew.setCart(cart);
                cartDetailNew.setProduct(product);
                cartDetailNew.setTitle(product.getTitle());
                cartDetailNew.setPrice(product.getPrice());
                cartDetailNew.setUnit(product.getUnit());
                cartDetailNew.setQuantity(cartItemReqDTO.getQuantity());
                cartDetailNew.setAmount(amount);
                cartDetailRepository.save(cartDetailNew);

                BigDecimal totalAmount = cart.getTotalAmount().add(amount);
                cart.setTotalAmount(totalAmount);
                return cartRepository.save(cart);
            }
        }
    }

    @Override
    public Optional<Cart> findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }
}
