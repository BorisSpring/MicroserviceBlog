package com.main.blogservice.service;

import com.main.blogservice.domain.Category;
import com.main.blogservice.exceptions.CategoryException;
import com.main.blogservice.model.CategoryDto;

import java.util.List;

public interface CategoryService {

    Category addCategory(String category) throws CategoryException;

    void deleteCategory(Integer categoryId) throws CategoryException;

    void updateCategory(Integer userId, String categoryName) throws CategoryException;

    List<CategoryDto> findAllCategories();

    void updateCategoryOrder(Integer categoryId, int orderNumber) throws CategoryException;
}
