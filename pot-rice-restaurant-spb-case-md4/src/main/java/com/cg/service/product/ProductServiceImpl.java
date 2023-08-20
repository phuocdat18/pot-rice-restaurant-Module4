package com.cg.service.product;

import com.cg.exception.DataInputException;
import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import com.cg.model.dto.product.ProductCreateReqDTO;
import com.cg.model.dto.product.ProductDTO;
import com.cg.model.dto.product.ProductUpdateReqDTO;
import com.cg.repository.ProductAvatarRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.category.ICategoryService;
import com.cg.service.upload.IUploadService;
import com.cg.utils.AppUtils;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAvatarRepository productAvatarRepository;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private ICategoryService categoryService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

        @Override
    public Product create(ProductCreateReqDTO productCreateReqDTO, Category category) {

        ProductAvatar productAvatar = new ProductAvatar();
        productAvatarRepository.save(productAvatar);

        uploadAndSaveProductImage(productCreateReqDTO, productAvatar);

        Product product = productCreateReqDTO.toProduct(category);
        product.setQuantity(100L);
        product.setProductAvatar(productAvatar);

        productRepository.save(product);

        return product;
    }

    @Override
    public Product update(Long id, ProductUpdateReqDTO productUpdateReqDTO, Category category) {
        ProductAvatar productAvatar = new ProductAvatar();

        productAvatarRepository.save(productAvatar);

        uploadAndSaveProductImage(productUpdateReqDTO.toProductCreateReqDTO(), productAvatar);

        Product product = productUpdateReqDTO.toProductChangeImage(category);
        Product oldProduct = productRepository.findById(id).get();
        product.setId(id);
        product.setQuantity(oldProduct.getQuantity());
        product.setProductAvatar(productAvatar);

        productRepository.save(product);

        return product;
    }

    @Override
    public List<ProductDTO> findAllProductDTO() {
        return productRepository.findAllProductDTO();
    }

    @Override
    public Boolean existsProductById(Long id) {
        return productRepository.existsProductById(id);
    }

    @Override
    public Page<ProductDTO> findAllProductDTOByKeyWordAndCategoryAndPrice(String search, List<Long> category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findAllProductDTOByKeyWordAndCategoryAndPrice(search, category, minPrice, maxPrice, pageable);
    }

    @Override
    public Page<ProductDTO> findAllProductDTOByKeyWordAndCategory(String search, List<Long> category, Pageable pageable) {
        return productRepository.findAllProductDTOByKeyWordAndCategory(search, category, pageable);
    }

    @Override
    public Page<ProductDTO> findAllProductDTOPage(Pageable pageable) {
        return productRepository.findAllProductDTOPage(pageable);
    }

    private void uploadAndSaveProductImage(ProductCreateReqDTO productCreateReqDTO, ProductAvatar productAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(productCreateReqDTO.getAvatar(), uploadUtils.buildImageUploadParams(productAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productAvatar.setFileName(productAvatar.getId() + "." + fileFormat);
            productAvatar.setFileUrl(fileUrl);
            productAvatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            productAvatar.setCloudId(productAvatar.getFileFolder() + "/" + productAvatar.getId());
            productAvatarRepository.save(productAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
        productAvatarRepository.delete(product.getProductAvatar());
    }

    @Override
    public void deleteById(Long id) {

    }
}
