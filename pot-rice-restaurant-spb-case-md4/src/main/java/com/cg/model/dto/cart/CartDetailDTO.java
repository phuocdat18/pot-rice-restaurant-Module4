package com.cg.model.dto.cart;

import com.cg.model.Product;
import com.cg.model.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDTO {
    private Long id;
    private ProductDTO productDTO;
    private String title;
    private String unit;
    private BigDecimal price;
    private Long quantity;
    private BigDecimal amount;

    public CartDetailDTO (Long id, Product product, String title, String unit, BigDecimal price, Long quantity, BigDecimal amount) {
        this.id = id;
        this.productDTO = product.toProductDTO();
        this.title = title;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }
}
