package com.main.blogservice.service;

import com.main.blogservice.domain.Category;
import com.main.blogservice.exceptions.CategoryException;
import com.main.blogservice.exceptions.NotFoundException;
import com.main.blogservice.model.CategoryDto;
import com.main.blogservice.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;


    @Override
    public Category addCategory(String categoryName) throws CategoryException {

        categoryName = categoryName.replaceAll("\"", "");

        if(categoryRepo.existsByName(categoryName))
            throw new CategoryException("There is alerdy category with same name!");

        return categoryRepo.save(Category.builder()
                                .order(null)
                                .name(categoryName)
                                .build());
    }

    @Transactional
    @Override
    public void deleteCategory(Integer categoryId) throws CategoryException {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found!"));

        category.getBlogs().forEach(blog -> {
                blog.setCategory(null);
        });

        categoryRepo.deleteById(categoryId);
    }

    @Override
    public void updateCategory(Integer categoryId, String categoryName) throws CategoryException {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new CategoryException("Category with id " + categoryId + " not found!"));

        if (categoryRepo.existsByName(categoryName))
            throw new CategoryException("Category with that name alerdy exists");

        category.setName(categoryName.replaceAll("\"", ""));
        categoryRepo.saveAndFlush(category);
    }

    @Override
    public void updateCategoryOrder (Integer categoryId, int orderNumber) throws CategoryException {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new CategoryException("Categoryi with id " + categoryId + " doesnt exists"));

        if(categoryRepo.existsByOrder(orderNumber))
            throw new CategoryException("There is alerdy category with same order number!");

        category.setOrder(orderNumber);
        categoryRepo.saveAndFlush(category);
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        return categoryRepo.findAllCategoryDto();
    }
}
