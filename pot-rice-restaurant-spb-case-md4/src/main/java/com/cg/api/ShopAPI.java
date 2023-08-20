package com.cg.api;

import com.cg.model.dto.product.ProductDTO;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopAPI {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ValidateUtils validateUtils;

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
