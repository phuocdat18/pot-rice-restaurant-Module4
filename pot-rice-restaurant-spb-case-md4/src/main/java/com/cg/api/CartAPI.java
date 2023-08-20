package com.cg.api;


import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.bill.BillReqDTO;
import com.cg.model.dto.cart.CartDetailChangeReqDTO;
import com.cg.model.dto.cart.CartDetailDTO;
import com.cg.model.dto.cart.CartItemReqDTO;
import com.cg.service.bill.IBillService;
import com.cg.service.billDetail.IBillDetailService;
import com.cg.service.cart.ICartService;
import com.cg.service.cartDetail.ICartDetailService;
import com.cg.service.product.IProductService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartAPI {

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartDetailService cartDetailService;

    @Autowired
    private IBillService billService;

    @Autowired
    private IBillDetailService billDetailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<List<?>> findAllCartDetail() {

        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);

        try {
            List<CartDetailDTO> cartDetailDTOS = cartDetailService.findAllCartDetailDTO(userOptional.get().getId());

            if (cartDetailDTOS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cartDetailDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<List<?>> addToCart(@RequestBody CartItemReqDTO cartItemReqDTO) {

        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);

        Long productId = cartItemReqDTO.getProductId();
        Optional<Product> productOptional = productService.findById(productId);

        if (productOptional.isEmpty()) {
            throw new DataInputException("Product invalid");
        }
        if (productOptional.get().getQuantity() < cartItemReqDTO.getQuantity()) {
            throw new DataInputException("Quantity invalid");
        }

        Product product = productOptional.get();

        Cart cart = cartService.addToCart(cartItemReqDTO, product, userOptional.get());

        try {
            List<CartDetailDTO> cartDetailDTOS = cartDetailService.findAllCartDetailDTO(userOptional.get().getId());

            if (cartDetailDTOS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cartDetailDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@Valid @RequestBody BillReqDTO billReqDTO) {
        String username = appUtils.getPrincipalUsername();      //dat

        Optional<User> userOptional = userService.findByUsername(username);

        Optional<Cart> cartOptional = cartService.findByUser(userOptional.get());


        if(cartOptional.isEmpty()) {
            throw new DataInputException("Cart invalid");
        }

        List<CartDetail> cartDetails = cartDetailService.findCartDetailsByCart(cartOptional.get());

        if(cartDetails.isEmpty()) {
            throw new DataInputException("CartDetail invalid");
        }
        for (CartDetail cartDetail : cartDetails) {
            Optional<Product> productOptional = productService.findById(cartDetail.getProduct().getId());
            Product product = productOptional.get();
            if (product.getQuantity() < cartDetail.getQuantity()) {
                throw new DataInputException("Số lượng sản phẩm " + cartDetail.getId() + " không đủ!");
            }
            Long quantityNew = product.getQuantity() - cartDetail.getQuantity();
            product.setQuantity(quantityNew);
            productService.save(product);
        }
        BigDecimal vat = cartOptional.get().getTotalAmount().multiply(BigDecimal.valueOf(0.1));
        BigDecimal totalBill =cartOptional.get().getTotalAmount().add(vat).add(BigDecimal.valueOf(15000)) ;

        Bill bill = billService.save(new Bill(totalBill, userOptional.get(), billReqDTO.getLocationRegionReqDTO().toLocationRegion(null) , billReqDTO.getStatus()));
        for (CartDetail cartDetail : cartDetails) {
            billDetailService.addBillDetail(new BillDetail(cartDetail.getProduct(), cartDetail.getTitle(), cartDetail.getUnit(), cartDetail.getPrice(), cartDetail.getQuantity(),cartDetail.getAmount(), bill), cartDetail);
        }
        cartService.delete(cartOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PatchMapping("/bill/{id}")
    public ResponseEntity<?> updateBillStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Optional<Bill> billOptional = billService.findById(id);

        if (billOptional.isEmpty()) {
            throw new DataInputException("Bill not found with id: " + id);
        }

        Bill bill = billOptional.get();
        bill.setStatus(EPayment.valueOf(request.get("status")));
        billService.save(bill);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<?>> delete(@PathVariable String id) throws IOException {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Sản phẩm không hợp lệ");
        }
        Long cartDetailId = Long.parseLong(id);

        Optional<CartDetail> cartDetailOptional = cartDetailService.findById(cartDetailId);

        if (cartDetailOptional.isPresent()) {
            cartDetailService.delete(cartDetailOptional.get());
        } else {
            throw new DataInputException("Invalid product information");
        }
        try {
            List<CartDetailDTO> cartDetailDTOS = cartDetailService.findAllCartDetailDTO(userOptional.get().getId());

            if (cartDetailDTOS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cartDetailDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/change-quantity/{id}")
    public ResponseEntity<List<?>> changeQuantity (@PathVariable String id, @RequestBody CartDetailChangeReqDTO cartDetailChangeReqDTO) throws IOException {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Cart detail không hợp lệ");
        }
        Long cartDetailId = Long.parseLong(id);

        Optional<CartDetail> cartDetailOptional = cartDetailService.findById(cartDetailId);

        CartDetail cartDetail = cartDetailOptional.get();
        BigDecimal newAmout = cartDetail.getPrice().multiply(BigDecimal.valueOf(cartDetailChangeReqDTO.getQuantity()));

        cartDetail.setQuantity(cartDetailChangeReqDTO.getQuantity());
        cartDetail.setAmount(newAmout);

        cartDetailService.save(cartDetail);

        try {
            List<CartDetailDTO> cartDetailDTOS = cartDetailService.findAllCartDetailDTO(userOptional.get().getId());

            if (cartDetailDTOS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cartDetailDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
