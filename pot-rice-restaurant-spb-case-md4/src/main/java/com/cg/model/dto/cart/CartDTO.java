package com.cg.model.dto.cart;

import com.cg.model.Cart;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CartDTO {
    private Long id;
    private BigDecimal totalAmount;

    public Cart toCart(User user) {
        return new Cart()
                .setId(id)
                .setTotalAmount(totalAmount)
                .setUser(user);
    }
}
