package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.dto.product.*;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/dashboard")
public class DashboardAPI {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ValidateUtils validateUtils;
    @GetMapping
    public ResponseEntity<?> getAllProducts() {

        List<ProductDTO> productDTOS = productService.findAllProductDTO();

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable String id) {
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }
        Long productId = Long.parseLong(id);

        try {
            Optional<Product> product = productService.findById(productId);

            if (product.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(product.get().toProductDTO(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute ProductCreateReqDTO productCreateReqDTO, BindingResult bindingResult) {

        new ProductCreateReqDTO().validate(productCreateReqDTO, bindingResult);

        Category category = categoryService.findById(productCreateReqDTO.getCategoryId()).orElseThrow(() -> {
            throw new DataInputException("Danh mục không tồn tại");
        });

        Product product = productService.create(productCreateReqDTO, category);
        ProductCreateResDTO productCreateResDTO = product.toProductCreateResDTO();

        return new ResponseEntity<>(productCreateResDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @ModelAttribute ProductUpdateReqDTO productUpdateReqDTO, BindingResult bindingResult) {

        new ProductUpdateReqDTO().validate(productUpdateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }
        Long productId = Long.parseLong(id);

        Optional<Product> productOptional = productService.findById(productId);

        Category category = categoryService.findById(productUpdateReqDTO.getCategoryId()).orElseThrow(() -> {
            throw new DataInputException("Danh mục không tồn tại");
        });

        if (productUpdateReqDTO.getAvatar() == null) {
            Product product = productUpdateReqDTO.toProductNoChangeImage(category);
            product.setId(productOptional.get().getId());
            product.setProductAvatar(productOptional.get().getProductAvatar());
            productService.save(product);
            return new ResponseEntity<>(product.toProductCreateResDTO(), HttpStatus.OK);
        }

        if (productOptional.isPresent()) {
            Product product = productService.update(productOptional.get().getId() ,productUpdateReqDTO, category);
            ProductUpdateResDTO productUpdateResDTO = product.toProductUpdateResDTO();

            return new ResponseEntity<>(productUpdateResDTO, HttpStatus.OK);
        } else {
            throw new DataInputException("Invalid product information");
        }

    }

}
