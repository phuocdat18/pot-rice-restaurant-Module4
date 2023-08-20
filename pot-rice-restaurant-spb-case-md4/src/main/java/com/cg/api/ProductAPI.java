package com.cg.api;


import com.cg.exception.DataInputException;
import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.dto.product.*;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ValidateUtils validateUtils;

//    @GetMapping
//    public ResponseEntity<?> getAll() {
//        List<ProductDTO> productDTOS = productService.findAllProductDTO();
//
//        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProductDTO(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        pageSize = 8;
        try {
            Page<ProductDTO> productDTOS = productService.findAllProductDTOPage(PageRequest.of(page - 1, pageSize));

            if (productDTOS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

//    @PostMapping
//    public ResponseEntity<?> addNew(@ModelAttribute ProductCreateReqDTO productCreateReqDTO) {
//
//        productService.create(productCreateReqDTO);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws IOException {
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }
        Long productId = Long.parseLong(id);

        Optional<Product> product = productService.findById(productId);

        if (product.isPresent()) {
            productService.delete(product.get());

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new DataInputException("Invalid product information");
        }
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
            product.setQuantity(productOptional.get().getQuantity());
            productService.save(product);
            return new ResponseEntity<>(product.toProductUpdateResDTO(), HttpStatus.OK);
        }

        if (productOptional.isPresent()) {
            Product product = productService.update(productOptional.get().getId(), productUpdateReqDTO, category);
            ProductUpdateResDTO productUpdateResDTO = product.toProductUpdateResDTO();

            return new ResponseEntity<>(productUpdateResDTO, HttpStatus.OK);
        } else {
            throw new DataInputException("Invalid product information");
        }

    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> search(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "ids", required = false) String categories,
            @RequestParam(name = "minPrice", required = false) String minPrice,
            @RequestParam(name = "maxPrice", required = false) String maxPrice,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {

        List<Long> ids = new ArrayList<>();
        if (categories == "") {
            ids.add(1L);
            ids.add(2L);
        } else {
            String[] categoryIds = categories.split(",");
            for (int i = 0; i < categoryIds.length; i++) {
                ids.add(Long.parseLong(categoryIds[i]));
            }
        }
        if (maxPrice == "" || minPrice == "") {
            minPrice = "0";
            maxPrice = "999999999";
        }
        try {
            Page<ProductDTO> productDTOS = productService.findAllProductDTOByKeyWordAndCategoryAndPrice(search, ids, BigDecimal.valueOf(Long.parseLong(minPrice)), BigDecimal.valueOf(Long.parseLong(maxPrice)), PageRequest.of(page - 1, pageSize));

            if (productDTOS.isEmpty()) {
                return new ResponseEntity<>(productDTOS, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
