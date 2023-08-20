package com.cg.service.product;

import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.dto.product.ProductCreateReqDTO;
import com.cg.model.dto.product.ProductDTO;
import com.cg.model.dto.product.ProductUpdateReqDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService extends IGeneralService<Product, Long> {

    Product create(ProductCreateReqDTO productCreateReqDTO, Category category);
//    Product create(ProductCreateReqDTO productCreateReqDTO);

    Product update(Long id, ProductUpdateReqDTO productUpdateReqDTO, Category category);

    List<ProductDTO> findAllProductDTO();
    Boolean existsProductById(Long id);

    Page<ProductDTO> findAllProductDTOByKeyWordAndCategoryAndPrice (String search, List<Long> category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<ProductDTO> findAllProductDTOByKeyWordAndCategory (String search, List<Long> category, Pageable pageable);
    Page<ProductDTO> findAllProductDTOPage(Pageable pageable);
}
