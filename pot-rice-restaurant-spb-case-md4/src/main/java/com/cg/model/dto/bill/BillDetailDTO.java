package com.cg.model.dto.bill;

import com.cg.model.Bill;
import com.cg.model.EPayment;
import com.cg.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class BillDetailDTO {
    private Long id;
    private Product product;
    private String title;
    private String unit;
    private BigDecimal price;
    private Long quantity;
    private BigDecimal amount;
    private BillDTO billDTO;
    private EPayment status;

    public BillDetailDTO(Long id, Product product, String title, String unit, BigDecimal price, Long quantity, BigDecimal amount, Bill bill) {
        this.id = id;
        this.product = product;
        this.title = title;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.billDTO= bill.toBillDTO();
    }

    public BillDetailDTO(Long id, Product product, String title, String unit, BigDecimal price, Long quantity, BigDecimal amount, Bill bill, EPayment status) {
        this.id = id;
        this.product = product;
        this.title = title;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.billDTO= bill.toBillDTO();
        this.status = status;
    }

}
