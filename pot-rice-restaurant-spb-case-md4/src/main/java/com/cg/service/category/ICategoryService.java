package com.cg.service.category;

import com.cg.model.Category;
import com.cg.model.dto.category.CategoryDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICategoryService extends IGeneralService<Category, Long> {

    List<CategoryDTO> findAllCategoryDTO();
}