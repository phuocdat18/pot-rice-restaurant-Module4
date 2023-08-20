package com.cg.api;

import com.cg.model.dto.bill.BillDTO;
import com.cg.model.dto.product.ProductDTO;
import com.cg.service.bill.IBillService;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/revenue")
public class RevenueAPI {

    @Autowired
    private IProductService productService;

    @Autowired
    private IBillService billService;
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<?> getAllBills() {

        List<BillDTO> billDTOS = billService.findAllBillDTO();

        return new ResponseEntity<>(billDTOS, HttpStatus.OK);
    }



}
